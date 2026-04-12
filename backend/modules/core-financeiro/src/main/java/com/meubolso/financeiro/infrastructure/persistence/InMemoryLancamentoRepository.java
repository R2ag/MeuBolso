package com.meubolso.financeiro.infrastructure.persistence;

import com.meubolso.financeiro.domain.model.Lancamento;
import com.meubolso.financeiro.domain.repository.LancamentoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryLancamentoRepository implements LancamentoRepository {

    private final Map<String, List<Lancamento>> storage = new ConcurrentHashMap<>();

    @Override
    public List<Lancamento> findByUserId(String userId) {
        return new ArrayList<>(storage.getOrDefault(userId, List.of()));
    }

    @Override
    public Optional<Lancamento> findByIdAndUserId(UUID id, String userId) {
        return storage.getOrDefault(userId, List.of()).stream()
                .filter(lancamento -> lancamento.getId().equals(id))
                .findFirst();
    }

    @Override
    public Lancamento save(Lancamento lancamento) {
        storage.compute(lancamento.getUserId(), (userId, lancamentos) -> {
            if (lancamentos == null) {
                lancamentos = new ArrayList<>();
            } else {
                lancamentos = lancamentos.stream()
                        .filter(existing -> !existing.getId().equals(lancamento.getId()))
                        .collect(Collectors.toCollection(ArrayList::new));
            }
            lancamentos.add(lancamento);
            return lancamentos;
        });
        return lancamento;
    }

    @Override
    public void deleteByIdAndUserId(UUID id, String userId) {
        storage.computeIfPresent(userId, (key, lancamentos) -> {
            lancamentos.removeIf(lancamento -> lancamento.getId().equals(id));
            return lancamentos;
        });
    }
}
