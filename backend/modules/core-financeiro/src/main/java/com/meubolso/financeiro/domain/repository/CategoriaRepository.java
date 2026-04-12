package com.meubolso.financeiro.domain.repository;

import com.meubolso.financeiro.domain.model.Categoria;

import java.util.List;
import java.util.UUID;

public interface CategoriaRepository {

    List<Categoria> findByUserId(String userId);

    Categoria save(Categoria categoria);

    void deleteByIdAndUserId(UUID id, String userId);
}
