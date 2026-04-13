package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.AcompanharOrcamentoRequest;
import com.meubolso.financeiro.application.dto.OrcamentoResumoResponse;
import com.meubolso.financeiro.domain.model.Lancamento;
import com.meubolso.financeiro.domain.model.LancamentoStatus;
import com.meubolso.financeiro.domain.model.Orcamento;
import com.meubolso.financeiro.domain.repository.LancamentoRepository;
import com.meubolso.financeiro.domain.repository.OrcamentoRepository;
import com.meubolso.shared.security.UserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AcompanharOrcamentoUseCaseTest {

    private OrcamentoRepository orcamentoRepository;
    private LancamentoRepository lancamentoRepository;
    private UserContext userContext;
    private AcompanharOrcamentoUseCase useCase;

    @BeforeEach
    void setUp() {
        orcamentoRepository = mock(OrcamentoRepository.class);
        lancamentoRepository = mock(LancamentoRepository.class);
        userContext = mock(UserContext.class);
        useCase = new AcompanharOrcamentoUseCase(orcamentoRepository, lancamentoRepository, userContext);
    }

    @Test
    void shouldReturnBudgetSummaryForMonth() {
        String userId = "user-2";
        when(userContext.getUserId()).thenReturn(userId);

        AcompanharOrcamentoRequest request = new AcompanharOrcamentoRequest();
        request.setAno(2026);
        request.setMes(4);

        Orcamento orc1 = new Orcamento(UUID.randomUUID(), userId, "Alimentação", 2026, 4, new BigDecimal("1200.00"));
        Orcamento orc2 = new Orcamento(UUID.randomUUID(), userId, "Transporte", 2026, 4, new BigDecimal("300.00"));

        Lancamento lancamento1 = new Lancamento(UUID.randomUUID(), userId, "Supermercado", new BigDecimal("-200.00"), LocalDate.of(2026, 4, 10), "Conta corrente", "Alimentação", LancamentoStatus.CONFIRMADO);
        Lancamento lancamento2 = new Lancamento(UUID.randomUUID(), userId, "Uber", new BigDecimal("-100.00"), LocalDate.of(2026, 4, 15), "Conta corrente", "Transporte", LancamentoStatus.CONFIRMADO);
        Lancamento lancamento3 = new Lancamento(UUID.randomUUID(), userId, "Salário", new BigDecimal("2000.00"), LocalDate.of(2026, 4, 1), "Conta salário", "Renda", LancamentoStatus.CONFIRMADO);

        when(orcamentoRepository.findByUserId(userId)).thenReturn(List.of(orc1, orc2));
        when(lancamentoRepository.findByUserId(userId)).thenReturn(List.of(lancamento1, lancamento2, lancamento3));

        List<OrcamentoResumoResponse> resumo = useCase.acompanhar(request);

        assertEquals(2, resumo.size());
        assertTrue(resumo.stream().anyMatch(item -> item.getCategoria().equals("Alimentação") && item.getRealizado().compareTo(new BigDecimal("200.00")) == 0 && item.getSaldoRestante().compareTo(new BigDecimal("1000.00")) == 0));
        assertTrue(resumo.stream().anyMatch(item -> item.getCategoria().equals("Transporte") && item.getRealizado().compareTo(new BigDecimal("100.00")) == 0 && item.getSaldoRestante().compareTo(new BigDecimal("200.00")) == 0));

        verify(orcamentoRepository).findByUserId(userId);
        verify(lancamentoRepository).findByUserId(userId);
    }
}
