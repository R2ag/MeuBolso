package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.ProjecaoSaldoResponse;
import com.meubolso.financeiro.domain.model.ContaPagar;
import com.meubolso.financeiro.domain.model.ContaReceber;
import com.meubolso.financeiro.domain.model.Divida;
import com.meubolso.financeiro.domain.model.Lancamento;
import com.meubolso.financeiro.domain.model.LancamentoStatus;
import com.meubolso.financeiro.domain.model.Parcela;
import com.meubolso.financeiro.domain.model.ParcelaStatus;
import com.meubolso.financeiro.domain.repository.ContaPagarRepository;
import com.meubolso.financeiro.domain.repository.ContaReceberRepository;
import com.meubolso.financeiro.domain.repository.DividaRepository;
import com.meubolso.financeiro.domain.repository.LancamentoRepository;
import com.meubolso.shared.security.UserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalcularProjecaoSaldoUseCaseTest {

    private LancamentoRepository lancamentoRepository;
    private ContaReceberRepository contaReceberRepository;
    private ContaPagarRepository contaPagarRepository;
    private DividaRepository dividaRepository;
    private UserContext userContext;
    private CalcularProjecaoSaldoUseCase useCase;

    @BeforeEach
    void setUp() {
        lancamentoRepository = mock(LancamentoRepository.class);
        contaReceberRepository = mock(ContaReceberRepository.class);
        contaPagarRepository = mock(ContaPagarRepository.class);
        dividaRepository = mock(DividaRepository.class);
        userContext = mock(UserContext.class);
        useCase = new CalcularProjecaoSaldoUseCase(lancamentoRepository, contaReceberRepository, contaPagarRepository, dividaRepository, userContext);
    }

    @Test
    void shouldCalculateProjectedBalance() {
        String userId = "user-3";
        when(userContext.getUserId()).thenReturn(userId);

        Lancamento receita = new Lancamento(UUID.randomUUID(), userId, "Salário", new BigDecimal("1200.00"), LocalDate.of(2026, 4, 1), "Conta salário", "Renda", LancamentoStatus.CONFIRMADO);
        Lancamento despesa = new Lancamento(UUID.randomUUID(), userId, "Mercado", new BigDecimal("-300.00"), LocalDate.of(2026, 4, 5), "Conta corrente", "Alimentação", LancamentoStatus.CONFIRMADO);

        ContaReceber receivable = new ContaReceber(UUID.randomUUID(), userId, "Fatura cliente", new BigDecimal("500.00"), LocalDate.of(2026, 4, 20), "Renda");
        ContaPagar payable = new ContaPagar(UUID.randomUUID(), userId, "Conta de energia", new BigDecimal("200.00"), LocalDate.of(2026, 4, 25), "Casa");

        Divida divida = new Divida(UUID.randomUUID(), userId, "Empréstimo", new BigDecimal("1000.00"), new BigDecimal("0.00"), 2, LocalDate.of(2026, 4, 10), "Dívida", List.of(
                new Parcela(UUID.randomUUID(), userId, UUID.randomUUID(), 1, new BigDecimal("100.00"), LocalDate.of(2026, 4, 10), ParcelaStatus.PENDENTE, null),
                new Parcela(UUID.randomUUID(), userId, UUID.randomUUID(), 2, new BigDecimal("100.00"), LocalDate.of(2026, 5, 10), ParcelaStatus.PAGA, UUID.randomUUID())
        ));

        when(lancamentoRepository.findByUserId(userId)).thenReturn(List.of(receita, despesa));
        when(contaReceberRepository.findByUserId(userId)).thenReturn(List.of(receivable));
        when(contaPagarRepository.findByUserId(userId)).thenReturn(List.of(payable));
        when(dividaRepository.findByUserId(userId)).thenReturn(List.of(divida));

        ProjecaoSaldoResponse response = useCase.calcular();

        assertEquals(new BigDecimal("900.00"), response.getSaldoAtual());
        assertEquals(new BigDecimal("500.00"), response.getEntradasFuturas());
        assertEquals(new BigDecimal("300.00"), response.getSaidasFuturas());
        assertEquals(new BigDecimal("1100.00"), response.getSaldoProjetado());

        verify(lancamentoRepository).findByUserId(userId);
        verify(contaReceberRepository).findByUserId(userId);
        verify(contaPagarRepository).findByUserId(userId);
        verify(dividaRepository).findByUserId(userId);
    }
}
