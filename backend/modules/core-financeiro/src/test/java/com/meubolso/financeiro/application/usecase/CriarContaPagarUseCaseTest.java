package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.ContaPagarRequest;
import com.meubolso.financeiro.domain.model.ContaPagar;
import com.meubolso.financeiro.domain.repository.ContaPagarRepository;
import com.meubolso.shared.security.UserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CriarContaPagarUseCaseTest {

    private ContaPagarRepository contaPagarRepository;
    private UserContext userContext;
    private CriarContaPagarUseCase useCase;

    @BeforeEach
    void setUp() {
        contaPagarRepository = mock(ContaPagarRepository.class);
        userContext = mock(UserContext.class);
        useCase = new CriarContaPagarUseCase(contaPagarRepository, userContext);
    }

    @Test
    void shouldCreateContaPagarForAuthenticatedUser() {
        String userId = "user-3";
        when(userContext.getUserId()).thenReturn(userId);
        when(contaPagarRepository.save(any(ContaPagar.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ContaPagarRequest request = new ContaPagarRequest();
        request.setDescricao("Aluguel");
        request.setValor(new BigDecimal("2000.00"));
        request.setDataVencimento(LocalDate.of(2026, 5, 10));
        request.setCategoria("Moradia");

        var response = useCase.criar(request);

        assertNotNull(response.getId());
        assertEquals(userId, response.getUserId());
        assertEquals("Aluguel", response.getDescricao());
        assertEquals(new BigDecimal("2000.00"), response.getValor());
        assertEquals(LocalDate.of(2026, 5, 10), response.getDataVencimento());
        assertEquals("Moradia", response.getCategoria());

        verify(contaPagarRepository).save(any(ContaPagar.class));
    }
}
