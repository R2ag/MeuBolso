package com.meubolso.financeiro.domain.repository;

import com.meubolso.financeiro.domain.model.Lancamento;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LancamentoRepository {

    List<Lancamento> findByUserId(String userId);

    Optional<Lancamento> findByIdAndUserId(UUID id, String userId);

    Lancamento save(Lancamento lancamento);

    void deleteByIdAndUserId(UUID id, String userId);
}
