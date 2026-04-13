package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.ContaReceberRequest;
import com.meubolso.financeiro.domain.model.ContaReceber;
import com.meubolso.financeiro.domain.repository.ContaReceberRepository;
import com.meubolso.shared.security.UserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CriarContaReceberUseCaseTest {

    private ContaReceberRepository contaReceberRepository;
    private UserContext userContext;
    private CriarContaReceberUseCase useCase;

    @BeforeEach
    void setUp() {
        contaReceberRepository = mock(ContaReceberRepository.class);
        userContext = mock(UserContext.class);
        useCase = new CriarContaReceberUseCase(contaReceberRepository, userContext);
    }

    @Test
    void shouldCreateContaReceberForAuthenticatedUser() {
        String userId = "user-4";
        when(userContext.getUserId()).thenReturn(userId);
        when(contaReceberRepository.save(any(ContaReceber.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ContaReceberRequest request = new ContaReceberRequest();
        request.setDescricao("Freelance");
        request.setValor(new BigDecimal("750.00"));
        request.setDataVencimento(LocalDate.of(2026, 5, 20));
        request.setCategoria("Renda");

        var response = useCase.criar(request);

        assertNotNull(response.getId());
        assertEquals(userId, response.getUserId());
        assertEquals("Freelance", response.getDescricao());
        assertEquals(new BigDecimal("750.00"), response.getValor());
        assertEquals(LocalDate.of(2026, 5, 20), response.getDataVencimento());
        assertEquals("Renda", response.getCategoria());

        verify(contaReceberRepository).save(any(ContaReceber.class));
    }
}
