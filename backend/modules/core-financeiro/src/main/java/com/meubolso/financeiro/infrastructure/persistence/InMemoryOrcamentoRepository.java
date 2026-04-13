package com.meubolso.financeiro.infrastructure.persistence;

import com.meubolso.financeiro.domain.model.Orcamento;
import com.meubolso.financeiro.domain.repository.OrcamentoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryOrcamentoRepository implements OrcamentoRepository {

    private final Map<String, List<Orcamento>> storage = new ConcurrentHashMap<>();

    @Override
    public Orcamento save(Orcamento orcamento) {
        storage.compute(orcamento.getUserId(), (userId, orcamentos) -> {
            if (orcamentos == null) {
                orcamentos = new ArrayList<>();
            } else {
                orcamentos = orcamentos.stream()
                        .filter(existing -> !existing.getId().equals(orcamento.getId()))
                        .collect(java.util.stream.Collectors.toCollection(ArrayList::new));
            }
            orcamentos.add(orcamento);
            return orcamentos;
        });
        return orcamento;
    }

    @Override
    public Optional<Orcamento> findByIdAndUserId(UUID id, String userId) {
        return storage.getOrDefault(userId, List.of()).stream()
                .filter(orcamento -> orcamento.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Orcamento> findByUserId(String userId) {
        return new ArrayList<>(storage.getOrDefault(userId, List.of()));
    }
}
