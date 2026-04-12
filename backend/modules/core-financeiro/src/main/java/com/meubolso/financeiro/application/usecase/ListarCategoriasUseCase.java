package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.CategoriaResponse;
import com.meubolso.financeiro.domain.repository.CategoriaRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarCategoriasUseCase {

    private final CategoriaRepository categoriaRepository;
    private final UserContext userContext;

    public ListarCategoriasUseCase(CategoriaRepository categoriaRepository, UserContext userContext) {
        this.categoriaRepository = categoriaRepository;
        this.userContext = userContext;
    }

    public List<CategoriaResponse> listar() {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }

        return categoriaRepository.findByUserId(userId).stream()
                .map(categoria -> new CategoriaResponse(categoria.getId(), categoria.getUserId(), categoria.getNome()))
                .collect(Collectors.toList());
    }
}
