package com.meubolso.financeiro.infrastructure.persistence;

import com.meubolso.financeiro.domain.model.ContaReceber;
import com.meubolso.financeiro.domain.repository.ContaReceberRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryContaReceberRepository implements ContaReceberRepository {

    private final Map<String, List<ContaReceber>> storage = new ConcurrentHashMap<>();

    @Override
    public ContaReceber save(ContaReceber contaReceber) {
        storage.compute(contaReceber.getUserId(), (userId, contas) -> {
            if (contas == null) {
                contas = new ArrayList<>();
            } else {
                contas = contas.stream()
                        .filter(existing -> !existing.getId().equals(contaReceber.getId()))
                        .collect(Collectors.toCollection(ArrayList::new));
            }
            contas.add(contaReceber);
            return contas;
        });
        return contaReceber;
    }

    @Override
    public Optional<ContaReceber> findByIdAndUserId(UUID id, String userId) {
        return storage.getOrDefault(userId, List.of()).stream()
                .filter(contaReceber -> contaReceber.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<ContaReceber> findByUserId(String userId) {
        return new ArrayList<>(storage.getOrDefault(userId, List.of()));
    }
}
