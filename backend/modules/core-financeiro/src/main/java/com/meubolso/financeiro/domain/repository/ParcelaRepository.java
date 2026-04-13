package com.meubolso.financeiro.domain.repository;

import com.meubolso.financeiro.domain.model.Parcela;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParcelaRepository {

    Parcela save(Parcela parcela);

    Optional<Parcela> findByIdAndUserId(UUID id, String userId);

    List<Parcela> findByDividaIdAndUserId(UUID dividaId, String userId);
}
