package com.meubolso.financeiro.infrastructure.persistence;

import com.meubolso.financeiro.domain.model.Lancamento;
import com.meubolso.financeiro.domain.repository.LancamentoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryLancamentoRepository implements LancamentoRepository {

    private final Map<String, List<Lancamento>> storage = new ConcurrentHashMap<>();

    @Override
    public List<Lancamento> findByUserId(String userId) {
        return new ArrayList<>(storage.getOrDefault(userId, List.of()));
    }

    @Override
    public Lancamento save(Lancamento lancamento) {
        storage.compute(lancamento.getUserId(), (userId, lancamentos) -> {
            if (lancamentos == null) {
                lancamentos = new ArrayList<>();
            }
            lancamentos.add(lancamento);
            return lancamentos;
        });
        return lancamento;
    }
}
