package com.meubolso.financeiro.infrastructure.persistence;

import com.meubolso.financeiro.domain.model.Categoria;
import com.meubolso.financeiro.domain.repository.CategoriaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaCategoriaRepositoryAdapter implements CategoriaRepository {

    private final SpringDataCategoriaRepository repository;

    public JpaCategoriaRepositoryAdapter(SpringDataCategoriaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Categoria> findByUserId(String userId) {
        return repository.findByUserId(userId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Categoria save(Categoria categoria) {
        return toDomain(repository.save(toEntity(categoria)));
    }

    @Override
    public void deleteByIdAndUserId(UUID id, String userId) {
        repository.deleteByIdAndUserId(id, userId);
    }

    private CategoriaEntity toEntity(Categoria categoria) {
        return new CategoriaEntity(categoria.getId(), categoria.getUserId(), categoria.getNome());
    }

    private Categoria toDomain(CategoriaEntity entity) {
        return new Categoria(entity.getId(), entity.getUserId(), entity.getNome());
    }
}
