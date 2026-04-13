package com.meubolso.classificacao.infrastructure.persistence;

import com.meubolso.classificacao.domain.model.ClassificacaoHistorico;
import com.meubolso.classificacao.domain.repository.ClassificacaoHistoricoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryClassificacaoHistoricoRepository implements ClassificacaoHistoricoRepository {

    private final Map<String, List<ClassificacaoHistorico>> storage = new ConcurrentHashMap<>();

    @Override
    public ClassificacaoHistorico save(ClassificacaoHistorico historico) {
        storage.compute(historico.getUserId(), (userId, historicos) -> {
            if (historicos == null) {
                historicos = new ArrayList<>();
            } else {
                historicos = historicos.stream()
                        .filter(existing -> !existing.getId().equals(historico.getId()))
                        .collect(Collectors.toCollection(ArrayList::new));
            }
            historicos.add(historico);
            return historicos;
        });
        return historico;
    }

    @Override
    public List<ClassificacaoHistorico> findByUserId(String userId) {
        return new ArrayList<>(storage.getOrDefault(userId, List.of()));
    }

    @Override
    public Optional<ClassificacaoHistorico> findByIdAndUserId(UUID id, String userId) {
        return storage.getOrDefault(userId, List.of()).stream()
                .filter(historico -> historico.getId().equals(id))
                .findFirst();
    }
}
