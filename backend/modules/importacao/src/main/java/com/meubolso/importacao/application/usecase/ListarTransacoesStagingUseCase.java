package com.meubolso.importacao.application.usecase;

import com.meubolso.classificacao.domain.model.ClassificacaoSugestao;
import com.meubolso.classificacao.domain.service.ClassificacaoService;
import com.meubolso.importacao.application.dto.TransacaoStagingResponse;
import com.meubolso.importacao.domain.model.TransacaoStaging;
import com.meubolso.importacao.domain.model.TransacaoStagingStatus;
import com.meubolso.importacao.domain.repository.TransacaoStagingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ListarTransacoesStagingUseCase {

    private final TransacaoStagingRepository stagingRepository;
    private final ClassificacaoService classificacaoService;

    public ListarTransacoesStagingUseCase(TransacaoStagingRepository stagingRepository, ClassificacaoService classificacaoService) {
        this.stagingRepository = stagingRepository;
        this.classificacaoService = classificacaoService;
    }

    public List<TransacaoStagingResponse> listar(UUID importacaoId, String userId) {
        if (importacaoId == null) {
            throw new IllegalArgumentException("Id da importação é obrigatório");
        }
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("Usuário não autenticado");
        }

        return stagingRepository.findByImportacaoId(importacaoId).stream()
                .filter(transacao -> userId.equals(transacao.getUserId()))
                .map(transacao -> {
                    ClassificacaoSugestao sugestao = null;
                    if (transacao.getStatus() == TransacaoStagingStatus.PENDENTE || "Sem categoria".equalsIgnoreCase(transacao.getCategoria())) {
                        sugestao = classificacaoService.sugerir(userId, transacao.getDescricao(), transacao.getConta());
                    }
                    return new TransacaoStagingResponse(
                            transacao.getId(),
                            transacao.getDescricao(),
                            transacao.getValor(),
                            transacao.getData(),
                            transacao.getConta(),
                            transacao.getCategoria(),
                            transacao.getStatus().name(),
                            sugestao != null ? sugestao.getCategoria() : null,
                            sugestao != null ? sugestao.getConfianca() : null,
                            sugestao != null ? sugestao.getFonte() : null
                    );
                })
                .collect(Collectors.toList());
    }
}
