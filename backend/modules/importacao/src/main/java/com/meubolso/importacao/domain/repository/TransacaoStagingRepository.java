package com.meubolso.importacao.domain.repository;

import com.meubolso.importacao.domain.model.TransacaoStaging;

import java.util.List;
import java.util.UUID;

public interface TransacaoStagingRepository {

    TransacaoStaging save(TransacaoStaging transacaoStaging);

    List<TransacaoStaging> findByImportacaoId(UUID importacaoId);

    java.util.Optional<TransacaoStaging> findById(UUID id);
}
