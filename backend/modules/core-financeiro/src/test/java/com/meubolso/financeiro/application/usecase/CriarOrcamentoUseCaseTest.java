package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.OrcamentoRequest;
import com.meubolso.financeiro.domain.model.Orcamento;
import com.meubolso.financeiro.domain.repository.OrcamentoRepository;
import com.meubolso.shared.security.UserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CriarOrcamentoUseCaseTest {

    private OrcamentoRepository orcamentoRepository;
    private UserContext userContext;
    private CriarOrcamentoUseCase useCase;

    @BeforeEach
    void setUp() {
        orcamentoRepository = mock(OrcamentoRepository.class);
        userContext = mock(UserContext.class);
        useCase = new CriarOrcamentoUseCase(orcamentoRepository, userContext);
    }

    @Test
    void shouldCreateOrcamentoForAuthenticatedUser() {
        String userId = "user-1";
        when(userContext.getUserId()).thenReturn(userId);
        when(orcamentoRepository.save(any(Orcamento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrcamentoRequest request = new OrcamentoRequest();
        request.setCategoria("Alimentação");
        request.setAno(2026);
        request.setMes(4);
        request.setValor(new BigDecimal("1500.00"));

        var response = useCase.criar(request);

        assertNotNull(response.getId());
        assertEquals(userId, response.getUserId());
        assertEquals("Alimentação", response.getCategoria());
        assertEquals(2026, response.getAno());
        assertEquals(4, response.getMes());
        assertEquals(new BigDecimal("1500.00"), response.getValor());

        verify(orcamentoRepository).save(any(Orcamento.class));
    }
}
