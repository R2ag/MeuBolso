package com.meubolso.financeiro.domain.repository;

import com.meubolso.financeiro.domain.model.Lancamento;

import java.util.List;

public interface LancamentoRepository {

    List<Lancamento> findByUserId(String userId);

    Lancamento save(Lancamento lancamento);
}
