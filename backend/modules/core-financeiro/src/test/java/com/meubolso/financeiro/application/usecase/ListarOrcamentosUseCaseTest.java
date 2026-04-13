package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.OrcamentoResponse;
import com.meubolso.financeiro.domain.model.Orcamento;
import com.meubolso.financeiro.domain.repository.OrcamentoRepository;
import com.meubolso.shared.security.UserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarOrcamentosUseCaseTest {

    private OrcamentoRepository orcamentoRepository;
    private UserContext userContext;
    private ListarOrcamentosUseCase useCase;

    @BeforeEach
    void setUp() {
        orcamentoRepository = mock(OrcamentoRepository.class);
        userContext = mock(UserContext.class);
        useCase = new ListarOrcamentosUseCase(orcamentoRepository, userContext);
    }

    @Test
    void shouldListOrcamentosForAuthenticatedUser() {
        String userId = "user-2";
        when(userContext.getUserId()).thenReturn(userId);

        Orcamento orc1 = new Orcamento(UUID.randomUUID(), userId, "Alimentação", 2026, 4, new BigDecimal("1200.00"));
        Orcamento orc2 = new Orcamento(UUID.randomUUID(), userId, "Transporte", 2026, 4, new BigDecimal("300.00"));

        when(orcamentoRepository.findByUserId(userId)).thenReturn(List.of(orc1, orc2));

        List<OrcamentoResponse> responses = useCase.listar();

        assertEquals(2, responses.size());
        assertTrue(responses.stream().anyMatch(response -> response.getCategoria().equals("Alimentação") && response.getValor().compareTo(new BigDecimal("1200.00")) == 0));
        assertTrue(responses.stream().anyMatch(response -> response.getCategoria().equals("Transporte") && response.getValor().compareTo(new BigDecimal("300.00")) == 0));

        verify(orcamentoRepository).findByUserId(userId);
    }
}
