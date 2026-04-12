package com.meubolso.financeiro.infrastructure.persistence;

import com.meubolso.financeiro.domain.model.Conta;
import com.meubolso.financeiro.domain.repository.ContaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryContaRepository implements ContaRepository {

    private final Map<String, List<Conta>> storage = new ConcurrentHashMap<>();

    @Override
    public List<Conta> findByUserId(String userId) {
        return new ArrayList<>(storage.getOrDefault(userId, List.of()));
    }

    @Override
    public Conta save(Conta conta) {
        storage.compute(conta.getUserId(), (userId, contas) -> {
            if (contas == null) {
                contas = new ArrayList<>();
            } else {
                contas = contas.stream()
                        .filter(existing -> !existing.getId().equals(conta.getId()))
                        .collect(Collectors.toCollection(ArrayList::new));
            }
            contas.add(conta);
            return contas;
        });
        return conta;
    }

    @Override
    public void deleteByIdAndUserId(UUID id, String userId) {
        storage.computeIfPresent(userId, (key, contas) -> {
            contas.removeIf(conta -> conta.getId().equals(id));
            return contas;
        });
    }
}
