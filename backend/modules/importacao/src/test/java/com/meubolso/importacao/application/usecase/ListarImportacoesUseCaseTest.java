package com.meubolso.importacao.application.usecase;

import com.meubolso.importacao.application.dto.ImportacaoResponse;
import com.meubolso.importacao.domain.model.Importacao;
import com.meubolso.importacao.domain.model.ImportacaoStatus;
import com.meubolso.importacao.domain.repository.ImportacaoRepository;
import com.meubolso.shared.security.UserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarImportacoesUseCaseTest {

    private ImportacaoRepository importacaoRepository;
    private UserContext userContext;
    private ListarImportacoesUseCase useCase;

    @BeforeEach
    void setUp() {
        importacaoRepository = mock(ImportacaoRepository.class);
        userContext = mock(UserContext.class);
        useCase = new ListarImportacoesUseCase(importacaoRepository, userContext);
    }

    @Test
    void shouldListImportacoesForAuthenticatedUser() {
        String userId = "user-1";
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        when(userContext.getUserId()).thenReturn(userId);
        when(importacaoRepository.findByUserId(userId)).thenReturn(List.of(
                new Importacao(id1, userId, "arquivo1.csv", Instant.now(), ImportacaoStatus.PENDENTE),
                new Importacao(id2, userId, "arquivo2.ofx", Instant.now(), ImportacaoStatus.REVISAO)
        ));

        List<ImportacaoResponse> responses = useCase.listar();

        assertEquals(2, responses.size());
        assertTrue(responses.stream().anyMatch(response -> response.getId().equals(id1) && response.getArquivoNome().equals("arquivo1.csv") && response.getStatus().equals("PENDENTE")));
        assertTrue(responses.stream().anyMatch(response -> response.getId().equals(id2) && response.getArquivoNome().equals("arquivo2.ofx") && response.getStatus().equals("REVISAO")));
        verify(importacaoRepository).findByUserId(userId);
    }
}
