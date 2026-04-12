package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.domain.model.Lancamento;
import com.meubolso.financeiro.domain.model.LancamentoStatus;
import com.meubolso.financeiro.domain.model.LancamentoTipo;
import com.meubolso.financeiro.domain.repository.LancamentoRepository;
import com.meubolso.shared.security.UserContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarLancamentosUseCaseTest {

    @Mock
    private LancamentoRepository lancamentoRepository;

    private UserContext userContext;
    private ListarLancamentosUseCase useCase;

    @BeforeEach
    void setup() {
        userContext = new UserContext();
        useCase = new ListarLancamentosUseCase(lancamentoRepository, userContext);
    }

    @AfterEach
    void cleanup() {
        userContext.clear();
    }

    @Test
    void shouldReturnLancamentosForAuthenticatedUser() {
        userContext.setUserId("user-1");

        Lancamento lancamento = new Lancamento(
                UUID.randomUUID(),
                "user-1",
                "Pagamento",
                new BigDecimal("50.00"),
                LocalDate.of(2026, 4, 12),
                "Conta A",
                "Categoria A",
                LancamentoStatus.CONFIRMADO
        );

        when(lancamentoRepository.findByUserId("user-1")).thenReturn(List.of(lancamento));

        var response = useCase.listar();

        assertThat(response).hasSize(1);
        assertThat(response.get(0).getId()).isEqualTo(lancamento.getId());
        assertThat(response.get(0).getTipo()).isEqualTo(LancamentoTipo.RECEITA);
    }

    @Test
    void shouldRejectUnauthenticatedUser() {
        assertThatThrownBy(useCase::listar)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("não autenticado");
    }
}
