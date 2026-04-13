package com.meubolso.financeiro.domain.repository;

import com.meubolso.financeiro.domain.model.Orcamento;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrcamentoRepository {

    Orcamento save(Orcamento orcamento);

    Optional<Orcamento> findByIdAndUserId(UUID id, String userId);

    List<Orcamento> findByUserId(String userId);
}
