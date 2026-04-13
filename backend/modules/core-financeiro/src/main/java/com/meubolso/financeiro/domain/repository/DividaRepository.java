package com.meubolso.financeiro.domain.repository;

import com.meubolso.financeiro.domain.model.Divida;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DividaRepository {

    Divida save(Divida divida);

    Optional<Divida> findByIdAndUserId(UUID id, String userId);

    List<Divida> findByUserId(String userId);
}
