package com.meubolso.importacao.application.usecase;

import com.meubolso.importacao.application.dto.ImportacaoResponse;
import com.meubolso.importacao.domain.model.Importacao;
import com.meubolso.importacao.domain.model.ImportacaoStatus;
import com.meubolso.importacao.domain.model.TransacaoStaging;
import com.meubolso.importacao.domain.model.TransacaoStagingStatus;
import com.meubolso.importacao.domain.repository.ImportacaoRepository;
import com.meubolso.importacao.domain.repository.TransacaoStagingRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RevisarImportacaoUseCase {

    private final ImportacaoRepository importacaoRepository;
    private final TransacaoStagingRepository stagingRepository;
    private final UserContext userContext;

    public RevisarImportacaoUseCase(ImportacaoRepository importacaoRepository,
                                    TransacaoStagingRepository stagingRepository,
                                    UserContext userContext) {
        this.importacaoRepository = importacaoRepository;
        this.stagingRepository = stagingRepository;
        this.userContext = userContext;
    }

    public ImportacaoResponse revisar(UUID importacaoId) {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }
        if (importacaoId == null) {
            throw new IllegalArgumentException("Id da importação é obrigatório");
        }

        Importacao importacao = importacaoRepository.findById(importacaoId)
                .orElseThrow(() -> new IllegalArgumentException("Importação não encontrada"));

        if (!userId.equals(importacao.getUserId())) {
            throw new IllegalArgumentException("Acesso negado à importação");
        }

        List<TransacaoStaging> transacoes = stagingRepository.findByImportacaoId(importacaoId);
        if (transacoes.isEmpty()) {
            throw new IllegalStateException("Não há transações em staging para revisão");
        }

        boolean allClassified = transacoes.stream()
                .allMatch(transacao -> transacao.getStatus() == TransacaoStagingStatus.CLASSIFICADA);

        if (!allClassified) {
            throw new IllegalStateException("Todas as transações devem ser classificadas antes da revisão");
        }

        Importacao revisada = importacao.revisar();
        importacaoRepository.save(revisada);

        return new ImportacaoResponse(
                revisada.getId(),
                revisada.getArquivoNome(),
                revisada.getCriadoEm(),
                revisada.getStatus().name()
        );
    }
}
