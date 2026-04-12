package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.domain.repository.LancamentoRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExcluirLancamentoUseCase {

    private final LancamentoRepository lancamentoRepository;
    private final UserContext userContext;

    public ExcluirLancamentoUseCase(LancamentoRepository lancamentoRepository, UserContext userContext) {
        this.lancamentoRepository = lancamentoRepository;
        this.userContext = userContext;
    }

    public void excluir(UUID id) {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }
        lancamentoRepository.deleteByIdAndUserId(id, userId);
    }
}
