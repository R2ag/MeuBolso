package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.CategoriaRequest;
import com.meubolso.financeiro.application.dto.CategoriaResponse;
import com.meubolso.financeiro.domain.model.Categoria;
import com.meubolso.financeiro.domain.repository.CategoriaRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CriarCategoriaUseCase {

    private final CategoriaRepository categoriaRepository;
    private final UserContext userContext;

    public CriarCategoriaUseCase(CategoriaRepository categoriaRepository, UserContext userContext) {
        this.categoriaRepository = categoriaRepository;
        this.userContext = userContext;
    }

    public CategoriaResponse criar(CategoriaRequest request) {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }

        Categoria categoria = new Categoria(UUID.randomUUID(), userId, request.getNome());
        Categoria salvo = categoriaRepository.save(categoria);
        return new CategoriaResponse(salvo.getId(), salvo.getUserId(), salvo.getNome());
    }
}
