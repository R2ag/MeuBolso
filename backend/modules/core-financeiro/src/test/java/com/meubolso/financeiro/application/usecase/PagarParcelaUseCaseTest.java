package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.PagarParcelaRequest;
import com.meubolso.financeiro.domain.model.Divida;
import com.meubolso.financeiro.domain.model.Lancamento;
import com.meubolso.financeiro.domain.model.LancamentoStatus;
import com.meubolso.financeiro.domain.model.Parcela;
import com.meubolso.financeiro.domain.model.ParcelaStatus;
import com.meubolso.financeiro.domain.repository.DividaRepository;
import com.meubolso.financeiro.domain.repository.LancamentoRepository;
import com.meubolso.financeiro.domain.repository.ParcelaRepository;
import com.meubolso.shared.security.UserContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagarParcelaUseCaseTest {

    @Mock
    private ParcelaRepository parcelaRepository;

    @Mock
    private DividaRepository dividaRepository;

    @Mock
    private LancamentoRepository lancamentoRepository;

    private UserContext userContext;
    private PagarParcelaUseCase useCase;

    @BeforeEach
    void setup() {
        userContext = new UserContext();
        useCase = new PagarParcelaUseCase(parcelaRepository, dividaRepository, lancamentoRepository, userContext);
    }

    @AfterEach
    void cleanup() {
        userContext.clear();
    }

    @Test
    void shouldPayParcelaAndCreateLancamento() {
        userContext.setUserId("user-2");

        UUID dividaId = UUID.randomUUID();
        Parcela parcela = new Parcela(
                UUID.randomUUID(),
                "user-2",
                dividaId,
                1,
                new BigDecimal("500.00"),
                LocalDate.of(2026, 6, 1),
                ParcelaStatus.PENDENTE,
                null
        );

        Divida divida = new Divida(
                dividaId,
                "user-2",
                "Financiamento",
                new BigDecimal("500.00"),
                new BigDecimal("0.00"),
                1,
                LocalDate.of(2026, 6, 1),
                "Financiamento",
                java.util.List.of(parcela)
        );

        when(parcelaRepository.findByIdAndUserId(parcela.getId(), "user-2")).thenReturn(Optional.of(parcela));
        when(dividaRepository.findByIdAndUserId(dividaId, "user-2")).thenReturn(Optional.of(divida));
        when(lancamentoRepository.save(any(Lancamento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PagarParcelaRequest request = new PagarParcelaRequest();
        request.setParcelaId(parcela.getId());
        request.setConta("Conta Corrente");
        request.setCategoria("Despesas");
        request.setDataPagamento(LocalDate.of(2026, 6, 2));

        var response = useCase.pagar(request);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(ParcelaStatus.PAGA);
        assertThat(response.getLancamentoId()).isNotNull();
        assertThat(response.getValor()).isEqualByComparingTo(new BigDecimal("500.00"));

        ArgumentCaptor<Lancamento> lancamentoCaptor = ArgumentCaptor.forClass(Lancamento.class);
        verify(lancamentoRepository).save(lancamentoCaptor.capture());
        assertThat(lancamentoCaptor.getValue().getValor()).isEqualByComparingTo(new BigDecimal("-500.00"));
        assertThat(lancamentoCaptor.getValue().getDescricao()).contains("Pagamento parcela");

        verify(parcelaRepository).save(any(Parcela.class));
    }
}
