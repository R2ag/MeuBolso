package com.meubolso.importacao.application.usecase;

import com.meubolso.importacao.application.parser.ImportacaoParser;
import com.meubolso.importacao.domain.model.Importacao;
import com.meubolso.importacao.domain.model.ImportacaoStatus;
import com.meubolso.importacao.domain.model.TransacaoStaging;
import com.meubolso.importacao.domain.repository.ImportacaoRepository;
import com.meubolso.importacao.domain.repository.TransacaoStagingRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ImportarTransacoesUseCase {

    private final ImportacaoRepository importacaoRepository;
    private final TransacaoStagingRepository stagingRepository;
    private final ImportacaoParser parser;

    public ImportarTransacoesUseCase(ImportacaoRepository importacaoRepository,
                                     TransacaoStagingRepository stagingRepository,
                                     ImportacaoParser parser) {
        this.importacaoRepository = importacaoRepository;
        this.stagingRepository = stagingRepository;
        this.parser = parser;
    }

    public Importacao importar(byte[] arquivo, String arquivoNome, String userId) {
        if (arquivo == null || arquivo.length == 0) {
            throw new IllegalArgumentException("Arquivo de importação não pode ser vazio");
        }
        if (arquivoNome == null || arquivoNome.isBlank()) {
            throw new IllegalArgumentException("Nome do arquivo é obrigatório");
        }
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("Usuário não autenticado");
        }

        UUID importacaoId = UUID.randomUUID();
        Importacao importacao = new Importacao(importacaoId, userId, arquivoNome, Instant.now(), ImportacaoStatus.PENDENTE);
        importacaoRepository.save(importacao);

        List<TransacaoStaging> transacoes = parser.parse(arquivo, importacaoId, userId, arquivoNome);
        transacoes.forEach(stagingRepository::save);

        return importacao;
    }
}
