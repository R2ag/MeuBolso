package com.meubolso.financeiro.domain.repository;

import com.meubolso.financeiro.domain.model.ContaReceber;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContaReceberRepository {

    ContaReceber save(ContaReceber contaReceber);

    Optional<ContaReceber> findByIdAndUserId(UUID id, String userId);

    List<ContaReceber> findByUserId(String userId);
}
