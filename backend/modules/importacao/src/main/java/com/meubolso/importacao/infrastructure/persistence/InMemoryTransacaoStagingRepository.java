package com.meubolso.importacao.infrastructure.persistence;

import com.meubolso.importacao.domain.model.TransacaoStaging;
import com.meubolso.importacao.domain.repository.TransacaoStagingRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class InMemoryTransacaoStagingRepository implements TransacaoStagingRepository {

    private final Map<UUID, List<TransacaoStaging>> storage = new HashMap<>();

    @Override
    public TransacaoStaging save(TransacaoStaging transacaoStaging) {
        List<TransacaoStaging> transacoes = storage.computeIfAbsent(transacaoStaging.getImportacaoId(), id -> new ArrayList<>());
        transacoes.removeIf(existing -> existing.getId().equals(transacaoStaging.getId()));
        transacoes.add(transacaoStaging);
        return transacaoStaging;
    }

    @Override
    public List<TransacaoStaging> findByImportacaoId(UUID importacaoId) {
        return new ArrayList<>(storage.getOrDefault(importacaoId, List.of()));
    }

    @Override
    public java.util.Optional<TransacaoStaging> findById(UUID id) {
        return storage.values().stream()
                .flatMap(List::stream)
                .filter(transacao -> transacao.getId().equals(id))
                .findFirst();
    }
}
