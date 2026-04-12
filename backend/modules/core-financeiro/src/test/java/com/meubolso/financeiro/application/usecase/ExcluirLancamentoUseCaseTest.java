package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.domain.repository.LancamentoRepository;
import com.meubolso.shared.security.UserContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExcluirLancamentoUseCaseTest {

    @Mock
    private LancamentoRepository lancamentoRepository;

    private UserContext userContext;
    private ExcluirLancamentoUseCase useCase;

    @BeforeEach
    void setup() {
        userContext = new UserContext();
        useCase = new ExcluirLancamentoUseCase(lancamentoRepository, userContext);
    }

    @AfterEach
    void cleanup() {
        userContext.clear();
    }

    @Test
    void shouldDeleteLancamentoForAuthenticatedUser() {
        userContext.setUserId("user-1");
        UUID id = UUID.randomUUID();

        useCase.excluir(id);

        verify(lancamentoRepository).deleteByIdAndUserId(id, "user-1");
    }

    @Test
    void shouldRejectUnauthenticatedUser() {
        UUID id = UUID.randomUUID();

        assertThatThrownBy(() -> useCase.excluir(id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("não autenticado");
    }
}
