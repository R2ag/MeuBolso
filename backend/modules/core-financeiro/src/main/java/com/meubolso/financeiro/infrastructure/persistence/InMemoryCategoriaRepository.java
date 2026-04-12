package com.meubolso.financeiro.infrastructure.persistence;

import com.meubolso.financeiro.domain.model.Categoria;
import com.meubolso.financeiro.domain.repository.CategoriaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryCategoriaRepository implements CategoriaRepository {

    private final Map<String, List<Categoria>> storage = new ConcurrentHashMap<>();

    @Override
    public List<Categoria> findByUserId(String userId) {
        return new ArrayList<>(storage.getOrDefault(userId, List.of()));
    }

    @Override
    public Categoria save(Categoria categoria) {
        storage.compute(categoria.getUserId(), (userId, categorias) -> {
            if (categorias == null) {
                categorias = new ArrayList<>();
            } else {
                categorias = categorias.stream()
                        .filter(existing -> !existing.getId().equals(categoria.getId()))
                        .collect(Collectors.toCollection(ArrayList::new));
            }
            categorias.add(categoria);
            return categorias;
        });
        return categoria;
    }

    @Override
    public void deleteByIdAndUserId(UUID id, String userId) {
        storage.computeIfPresent(userId, (key, categorias) -> {
            categorias.removeIf(categoria -> categoria.getId().equals(id));
            return categorias;
        });
    }
}
