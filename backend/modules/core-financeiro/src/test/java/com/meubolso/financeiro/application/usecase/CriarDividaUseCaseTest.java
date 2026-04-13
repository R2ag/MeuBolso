package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.DividaRequest;
import com.meubolso.financeiro.domain.model.Divida;
import com.meubolso.financeiro.domain.model.Parcela;
import com.meubolso.financeiro.domain.model.ParcelaStatus;
import com.meubolso.financeiro.domain.repository.DividaRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CriarDividaUseCaseTest {

    @Mock
    private DividaRepository dividaRepository;

    @Mock
    private ParcelaRepository parcelaRepository;

    private UserContext userContext;
    private CriarDividaUseCase useCase;

    @BeforeEach
    void setup() {
        userContext = new UserContext();
        useCase = new CriarDividaUseCase(dividaRepository, parcelaRepository, userContext);
    }

    @AfterEach
    void cleanup() {
        userContext.clear();
    }

    @Test
    void shouldCreateDividaWithCalculatedParcelas() {
        userContext.setUserId("user-1");

        DividaRequest request = new DividaRequest();
        request.setDescricao("Compra parcelada");
        request.setValorTotal(new BigDecimal("1000.00"));
        request.setTaxaJuros(new BigDecimal("10.00"));
        request.setNumeroParcelas(3);
        request.setDataPrimeiraParcela(LocalDate.of(2026, 5, 1));
        request.setCategoria("Dívida");

        when(dividaRepository.save(any(Divida.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(parcelaRepository.save(any(Parcela.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = useCase.criar(request);

        assertThat(response).isNotNull();
        assertThat(response.getDescricao()).isEqualTo("Compra parcelada");
        assertThat(response.getValorTotal()).isEqualByComparingTo(new BigDecimal("1000.00"));
        assertThat(response.getTaxaJuros()).isEqualByComparingTo(new BigDecimal("10.00"));
        assertThat(response.getNumeroParcelas()).isEqualTo(3);
        assertThat(response.getParcelas()).hasSize(3);
        assertThat(response.getParcelas()).extracting("valor")
                .containsExactly(new BigDecimal("366.67"), new BigDecimal("366.67"), new BigDecimal("366.66"));
        assertThat(response.getParcelas()).extracting("status").containsOnly(ParcelaStatus.PENDENTE);

        ArgumentCaptor<Divida> captor = ArgumentCaptor.forClass(Divida.class);
        verify(dividaRepository).save(captor.capture());
        assertThat(captor.getValue().getParcelas()).hasSize(3);
        verify(parcelaRepository, times(3)).save(any(Parcela.class));
    }
}
