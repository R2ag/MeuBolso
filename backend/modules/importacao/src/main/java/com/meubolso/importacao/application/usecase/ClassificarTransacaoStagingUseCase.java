package com.meubolso.importacao.application.usecase;

import com.meubolso.importacao.application.dto.AtualizarTransacaoStagingRequest;
import com.meubolso.importacao.application.dto.TransacaoStagingResponse;
import com.meubolso.importacao.domain.model.TransacaoStaging;
import com.meubolso.importacao.domain.repository.TransacaoStagingRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClassificarTransacaoStagingUseCase {

    private final TransacaoStagingRepository stagingRepository;
    private final UserContext userContext;

    public ClassificarTransacaoStagingUseCase(TransacaoStagingRepository stagingRepository, UserContext userContext) {
        this.stagingRepository = stagingRepository;
        this.userContext = userContext;
    }

    public TransacaoStagingResponse classificar(UUID importacaoId, UUID transacaoId, AtualizarTransacaoStagingRequest request) {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }
        if (importacaoId == null) {
            throw new IllegalArgumentException("Id da importação é obrigatório");
        }
        if (transacaoId == null) {
            throw new IllegalArgumentException("Id da transação é obrigatório");
        }

        TransacaoStaging transacao = stagingRepository.findById(transacaoId)
                .orElseThrow(() -> new IllegalArgumentException("Transação em staging não encontrada"));

        if (!userId.equals(transacao.getUserId())) {
            throw new IllegalArgumentException("Acesso negado à transação em staging");
        }
        if (!importacaoId.equals(transacao.getImportacaoId())) {
            throw new IllegalArgumentException("Transação não pertence à importação informada");
        }

        TransacaoStaging atualizada = transacao.classificar(request.getDescricao(), request.getConta(), request.getCategoria());
        stagingRepository.save(atualizada);

        return new TransacaoStagingResponse(
                atualizada.getId(),
                atualizada.getDescricao(),
                atualizada.getValor(),
                atualizada.getData(),
                atualizada.getConta(),
                atualizada.getCategoria(),
                atualizada.getStatus().name()
        );
    }
}
