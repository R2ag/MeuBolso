package com.meubolso.classificacao.domain.repository;

import com.meubolso.classificacao.domain.model.ClassificacaoHistorico;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClassificacaoHistoricoRepository {

    ClassificacaoHistorico save(ClassificacaoHistorico historico);

    List<ClassificacaoHistorico> findByUserId(String userId);

    Optional<ClassificacaoHistorico> findByIdAndUserId(UUID id, String userId);
}
