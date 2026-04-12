package com.meubolso.financeiro.infrastructure.persistence;

import com.meubolso.financeiro.domain.model.Lancamento;
import com.meubolso.financeiro.domain.repository.LancamentoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaLancamentoRepositoryAdapter implements LancamentoRepository {

    private final SpringDataLancamentoRepository repository;

    public JpaLancamentoRepositoryAdapter(SpringDataLancamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Lancamento> findByUserId(String userId) {
        return repository.findByUserId(userId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public java.util.Optional<Lancamento> findByIdAndUserId(UUID id, String userId) {
        return repository.findByIdAndUserId(id, userId)
                .map(this::toDomain);
    }

    @Override
    public Lancamento save(Lancamento lancamento) {
        return toDomain(repository.save(toEntity(lancamento)));
    }

    @Override
    public void deleteByIdAndUserId(UUID id, String userId) {
        repository.deleteByIdAndUserId(id, userId);
    }

    private LancamentoEntity toEntity(Lancamento lancamento) {
        return new LancamentoEntity(
                lancamento.getId(),
                lancamento.getUserId(),
                lancamento.getDescricao(),
                lancamento.getValor(),
                lancamento.getData(),
                lancamento.getConta(),
                lancamento.getCategoria(),
                lancamento.getStatus()
        );
    }

    private Lancamento toDomain(LancamentoEntity entity) {
        return new Lancamento(
                entity.getId(),
                entity.getUserId(),
                entity.getDescricao(),
                entity.getValor(),
                entity.getData(),
                entity.getConta(),
                entity.getCategoria(),
                entity.getStatus()
        );
    }
}
