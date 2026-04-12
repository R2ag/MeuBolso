package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.AtualizarLancamentoRequest;
import com.meubolso.financeiro.domain.model.Lancamento;
import com.meubolso.financeiro.domain.model.LancamentoStatus;
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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EditarLancamentoUseCaseTest {

    @Mock
    private LancamentoRepository lancamentoRepository;

    private UserContext userContext;
    private EditarLancamentoUseCase useCase;

    @BeforeEach
    void setup() {
        userContext = new UserContext();
        useCase = new EditarLancamentoUseCase(lancamentoRepository, userContext);
    }

    @AfterEach
    void cleanup() {
        userContext.clear();
    }

    @Test
    void shouldEditLancamentoWhenPending() {
        userContext.setUserId("user-1");
        UUID id = UUID.randomUUID();

        Lancamento existing = new Lancamento(
                id,
                "user-1",
                "Descrição anterior",
                new BigDecimal("100.00"),
                LocalDate.of(2026, 4, 1),
                "Conta A",
                "Categoria A",
                LancamentoStatus.PENDENTE
        );

        AtualizarLancamentoRequest request = new AtualizarLancamentoRequest();
        request.setDescricao("Descrição atualizada");
        request.setValor(new BigDecimal("120.00"));
        request.setData(LocalDate.of(2026, 4, 12));
        request.setConta("Conta B");
        request.setCategoria("Categoria B");

        when(lancamentoRepository.findByIdAndUserId(id, "user-1")).thenReturn(Optional.of(existing));
        when(lancamentoRepository.save(org.mockito.ArgumentMatchers.any())).thenAnswer(invocation -> invocation.getArgument(0));

        var response = useCase.editar(id, request);

        assertThat(response.getDescricao()).isEqualTo("Descrição atualizada");
        assertThat(response.getValor()).isEqualByComparingTo(new BigDecimal("120.00"));
        assertThat(response.getConta()).isEqualTo("Conta B");
        assertThat(response.getCategoria()).isEqualTo("Categoria B");
    }

    @Test
    void shouldRejectValueChangeForConfirmedLancamento() {
        userContext.setUserId("user-1");
        UUID id = UUID.randomUUID();

        Lancamento existing = new Lancamento(
                id,
                "user-1",
                "Descrição anterior",
                new BigDecimal("100.00"),
                LocalDate.of(2026, 4, 1),
                "Conta A",
                "Categoria A",
                LancamentoStatus.CONFIRMADO
        );

        AtualizarLancamentoRequest request = new AtualizarLancamentoRequest();
        request.setDescricao("Descrição atualizada");
        request.setValor(new BigDecimal("120.00"));
        request.setData(LocalDate.of(2026, 4, 12));
        request.setConta("Conta A");
        request.setCategoria("Categoria B");

        when(lancamentoRepository.findByIdAndUserId(id, "user-1")).thenReturn(Optional.of(existing));

        assertThatThrownBy(() -> useCase.editar(id, request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Valor não pode ser alterado");
    }

    @Test
    void shouldRejectUnauthenticatedUser() {
        UUID id = UUID.randomUUID();
        AtualizarLancamentoRequest request = new AtualizarLancamentoRequest();
        request.setDescricao("Descrição atualizada");
        request.setValor(new BigDecimal("120.00"));
        request.setData(LocalDate.of(2026, 4, 12));
        request.setConta("Conta A");
        request.setCategoria("Categoria B");

        assertThatThrownBy(() -> useCase.editar(id, request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("não autenticado");
    }
}
