package com.meubolso.importacao.infrastructure.persistence;

import com.meubolso.importacao.domain.model.Importacao;
import com.meubolso.importacao.domain.repository.ImportacaoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryImportacaoRepository implements ImportacaoRepository {

    private final Map<UUID, Importacao> storage = new HashMap<>();

    @Override
    public Importacao save(Importacao importacao) {
        storage.put(importacao.getId(), importacao);
        return importacao;
    }

    @Override
    public Optional<Importacao> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Importacao> findByUserId(String userId) {
        List<Importacao> result = new ArrayList<>();
        for (Importacao importacao : storage.values()) {
            if (userId.equals(importacao.getUserId())) {
                result.add(importacao);
            }
        }
        return result;
    }
}
