package com.meubolso.financeiro.infrastructure.persistence;

import com.meubolso.financeiro.domain.model.Conta;
import com.meubolso.financeiro.domain.repository.ContaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaContaRepositoryAdapter implements ContaRepository {

    private final SpringDataContaRepository repository;

    public JpaContaRepositoryAdapter(SpringDataContaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Conta> findByUserId(String userId) {
        return repository.findByUserId(userId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Conta save(Conta conta) {
        return toDomain(repository.save(toEntity(conta)));
    }

    @Override
    public void deleteByIdAndUserId(UUID id, String userId) {
        repository.deleteByIdAndUserId(id, userId);
    }

    private ContaEntity toEntity(Conta conta) {
        return new ContaEntity(conta.getId(), conta.getUserId(), conta.getNome());
    }

    private Conta toDomain(ContaEntity entity) {
        return new Conta(entity.getId(), entity.getUserId(), entity.getNome());
    }
}
