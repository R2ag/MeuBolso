package com.meubolso.financeiro.infrastructure.persistence;

import com.meubolso.financeiro.domain.model.Divida;
import com.meubolso.financeiro.domain.repository.DividaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryDividaRepository implements DividaRepository {

    private final Map<String, List<Divida>> storage = new ConcurrentHashMap<>();

    @Override
    public Divida save(Divida divida) {
        storage.compute(divida.getUserId(), (userId, dividas) -> {
            if (dividas == null) {
                dividas = new ArrayList<>();
            } else {
                dividas = dividas.stream()
                        .filter(existing -> !existing.getId().equals(divida.getId()))
                        .collect(Collectors.toCollection(ArrayList::new));
            }
            dividas.add(divida);
            return dividas;
        });
        return divida;
    }

    @Override
    public Optional<Divida> findByIdAndUserId(UUID id, String userId) {
        return storage.getOrDefault(userId, List.of()).stream()
                .filter(divida -> divida.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Divida> findByUserId(String userId) {
        return new ArrayList<>(storage.getOrDefault(userId, List.of()));
    }
}
