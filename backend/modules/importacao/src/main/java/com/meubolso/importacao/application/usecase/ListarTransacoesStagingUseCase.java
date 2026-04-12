package com.meubolso.importacao.application.usecase;

import com.meubolso.importacao.application.dto.TransacaoStagingResponse;
import com.meubolso.importacao.domain.model.TransacaoStaging;
import com.meubolso.importacao.domain.repository.TransacaoStagingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ListarTransacoesStagingUseCase {

    private final TransacaoStagingRepository stagingRepository;

    public ListarTransacoesStagingUseCase(TransacaoStagingRepository stagingRepository) {
        this.stagingRepository = stagingRepository;
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
                .map(transacao -> new TransacaoStagingResponse(
                        transacao.getId(),
                        transacao.getDescricao(),
                        transacao.getValor(),
                        transacao.getData(),
                        transacao.getConta(),
                        transacao.getCategoria(),
                        transacao.getStatus().name()
                ))
                .collect(Collectors.toList());
    }
}
