package com.meubolso.financeiro.infrastructure.persistence;

import com.meubolso.financeiro.domain.model.Parcela;
import com.meubolso.financeiro.domain.repository.ParcelaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryParcelaRepository implements ParcelaRepository {

    private final Map<String, List<Parcela>> storage = new ConcurrentHashMap<>();

    @Override
    public Parcela save(Parcela parcela) {
        storage.compute(parcela.getUserId(), (userId, parcelas) -> {
            if (parcelas == null) {
                parcelas = new ArrayList<>();
            } else {
                parcelas = parcelas.stream()
                        .filter(existing -> !existing.getId().equals(parcela.getId()))
                        .collect(Collectors.toCollection(ArrayList::new));
            }
            parcelas.add(parcela);
            return parcelas;
        });
        return parcela;
    }

    @Override
    public Optional<Parcela> findByIdAndUserId(UUID id, String userId) {
        return storage.getOrDefault(userId, List.of()).stream()
                .filter(parcela -> parcela.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Parcela> findByDividaIdAndUserId(UUID dividaId, String userId) {
        return storage.getOrDefault(userId, List.of()).stream()
                .filter(parcela -> parcela.getDividaId().equals(dividaId))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
