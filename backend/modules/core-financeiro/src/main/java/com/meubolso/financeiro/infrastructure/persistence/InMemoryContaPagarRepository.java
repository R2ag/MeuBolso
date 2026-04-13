package com.meubolso.financeiro.infrastructure.persistence;

import com.meubolso.financeiro.domain.model.ContaPagar;
import com.meubolso.financeiro.domain.repository.ContaPagarRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryContaPagarRepository implements ContaPagarRepository {

    private final Map<String, List<ContaPagar>> storage = new ConcurrentHashMap<>();

    @Override
    public ContaPagar save(ContaPagar contaPagar) {
        storage.compute(contaPagar.getUserId(), (userId, contas) -> {
            if (contas == null) {
                contas = new ArrayList<>();
            } else {
                contas = contas.stream()
                        .filter(existing -> !existing.getId().equals(contaPagar.getId()))
                        .collect(Collectors.toCollection(ArrayList::new));
            }
            contas.add(contaPagar);
            return contas;
        });
        return contaPagar;
    }

    @Override
    public Optional<ContaPagar> findByIdAndUserId(UUID id, String userId) {
        return storage.getOrDefault(userId, List.of()).stream()
                .filter(contaPagar -> contaPagar.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<ContaPagar> findByUserId(String userId) {
        return new ArrayList<>(storage.getOrDefault(userId, List.of()));
    }
}
