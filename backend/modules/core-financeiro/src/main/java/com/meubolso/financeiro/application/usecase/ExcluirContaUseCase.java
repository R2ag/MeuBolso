package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.domain.repository.ContaRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExcluirContaUseCase {

    private final ContaRepository contaRepository;
    private final UserContext userContext;

    public ExcluirContaUseCase(ContaRepository contaRepository, UserContext userContext) {
        this.contaRepository = contaRepository;
        this.userContext = userContext;
    }

    public void excluir(UUID id) {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }
        contaRepository.deleteByIdAndUserId(id, userId);
    }
}
