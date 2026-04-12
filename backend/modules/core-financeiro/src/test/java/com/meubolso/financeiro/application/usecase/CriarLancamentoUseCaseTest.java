package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.LancamentoRequest;
import com.meubolso.financeiro.domain.model.Lancamento;
import com.meubolso.financeiro.domain.model.LancamentoStatus;
import com.meubolso.financeiro.domain.model.LancamentoTipo;
import com.meubolso.financeiro.domain.repository.LancamentoRepository;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CriarLancamentoUseCaseTest {

    @Mock
    private LancamentoRepository lancamentoRepository;

    private UserContext userContext;
    private CriarLancamentoUseCase useCase;

    @BeforeEach
    void setup() {
        userContext = new UserContext();
        useCase = new CriarLancamentoUseCase(lancamentoRepository, userContext);
    }

    @AfterEach
    void cleanup() {
        userContext.clear();
    }

    @Test
    void shouldCreateLancamentoWithValidRequest() {
        userContext.setUserId("user-1");

        LancamentoRequest request = new LancamentoRequest();
        request.setDescricao("Teste");
        request.setValor(new BigDecimal("100.00"));
        request.setData(LocalDate.of(2026, 4, 12));
        request.setConta("Conta A");
        request.setCategoria("Categoria A");

        ArgumentCaptor<Lancamento> captor = ArgumentCaptor.forClass(Lancamento.class);
        when(lancamentoRepository.save(captor.capture())).thenAnswer(invocation -> invocation.getArgument(0));

        var response = useCase.criar(request);

        assertThat(response).isNotNull();
        assertThat(response.getUserId()).isEqualTo("user-1");
        assertThat(response.getDescricao()).isEqualTo("Teste");
        assertThat(response.getValor()).isEqualByComparingTo(new BigDecimal("100.00"));
        assertThat(response.getTipo()).isEqualTo(LancamentoTipo.RECEITA);
        assertThat(response.getStatus()).isEqualTo(LancamentoStatus.CONFIRMADO);

        Lancamento saved = captor.getValue();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getUserId()).isEqualTo("user-1");
        assertThat(saved.getConta()).isEqualTo("Conta A");
        assertThat(saved.getCategoria()).isEqualTo("Categoria A");
    }

    @Test
    void shouldRejectZeroValue() {
        userContext.setUserId("user-1");

        LancamentoRequest request = new LancamentoRequest();
        request.setDescricao("Teste");
        request.setValor(BigDecimal.ZERO);
        request.setData(LocalDate.of(2026, 4, 12));
        request.setConta("Conta A");
        request.setCategoria("Categoria A");

        assertThatThrownBy(() -> useCase.criar(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("diferente de zero");
    }

    @Test
    void shouldRejectUnauthenticatedUser() {
        LancamentoRequest request = new LancamentoRequest();
        request.setDescricao("Teste");
        request.setValor(new BigDecimal("100.00"));
        request.setData(LocalDate.of(2026, 4, 12));
        request.setConta("Conta A");
        request.setCategoria("Categoria A");

        assertThatThrownBy(() -> useCase.criar(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("não autenticado");
    }
}
