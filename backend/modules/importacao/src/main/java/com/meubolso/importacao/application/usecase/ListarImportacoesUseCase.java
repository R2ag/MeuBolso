package com.meubolso.importacao.application.usecase;

import com.meubolso.importacao.application.dto.ImportacaoResponse;
import com.meubolso.importacao.domain.repository.ImportacaoRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarImportacoesUseCase {

    private final ImportacaoRepository importacaoRepository;
    private final UserContext userContext;

    public ListarImportacoesUseCase(ImportacaoRepository importacaoRepository, UserContext userContext) {
        this.importacaoRepository = importacaoRepository;
        this.userContext = userContext;
    }

    public List<ImportacaoResponse> listar() {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }

        return importacaoRepository.findByUserId(userId).stream()
                .map(importacao -> new ImportacaoResponse(
                        importacao.getId(),
                        importacao.getArquivoNome(),
                        importacao.getCriadoEm(),
                        importacao.getStatus().name()
                ))
                .collect(Collectors.toList());
    }
}
