package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.domain.repository.CategoriaRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExcluirCategoriaUseCase {

    private final CategoriaRepository categoriaRepository;
    private final UserContext userContext;

    public ExcluirCategoriaUseCase(CategoriaRepository categoriaRepository, UserContext userContext) {
        this.categoriaRepository = categoriaRepository;
        this.userContext = userContext;
    }

    public void excluir(UUID id) {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }
        categoriaRepository.deleteByIdAndUserId(id, userId);
    }
}
