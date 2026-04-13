package com.meubolso.financeiro.domain.repository;

import com.meubolso.financeiro.domain.model.ContaPagar;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContaPagarRepository {

    ContaPagar save(ContaPagar contaPagar);

    Optional<ContaPagar> findByIdAndUserId(UUID id, String userId);

    List<ContaPagar> findByUserId(String userId);
}
