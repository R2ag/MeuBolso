package com.meubolso.importacao.domain.repository;

import com.meubolso.importacao.domain.model.Importacao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImportacaoRepository {

    Importacao save(Importacao importacao);

    Optional<Importacao> findById(UUID id);

    List<Importacao> findByUserId(String userId);
}
