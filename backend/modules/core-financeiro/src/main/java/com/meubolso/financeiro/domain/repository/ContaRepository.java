package com.meubolso.financeiro.domain.repository;

import com.meubolso.financeiro.domain.model.Conta;

import java.util.List;
import java.util.UUID;

public interface ContaRepository {

    List<Conta> findByUserId(String userId);

    Conta save(Conta conta);

    void deleteByIdAndUserId(UUID id, String userId);
}
