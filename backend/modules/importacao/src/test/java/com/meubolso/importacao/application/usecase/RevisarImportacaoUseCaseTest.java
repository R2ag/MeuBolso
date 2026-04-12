package com.meubolso.importacao.application.usecase;

import com.meubolso.importacao.application.dto.ImportacaoResponse;
import com.meubolso.importacao.domain.model.Importacao;
import com.meubolso.importacao.domain.model.ImportacaoStatus;
import com.meubolso.importacao.domain.model.TransacaoStaging;
import com.meubolso.importacao.domain.model.TransacaoStagingStatus;
import com.meubolso.importacao.domain.repository.ImportacaoRepository;
import com.meubolso.importacao.domain.repository.TransacaoStagingRepository;
import com.meubolso.shared.security.UserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RevisarImportacaoUseCaseTest {

    private ImportacaoRepository importacaoRepository;
    private TransacaoStagingRepository stagingRepository;
    private UserContext userContext;
    private RevisarImportacaoUseCase useCase;

    @BeforeEach
    void setUp() {
        importacaoRepository = mock(ImportacaoRepository.class);
        stagingRepository = mock(TransacaoStagingRepository.class);
        userContext = mock(UserContext.class);
        useCase = new RevisarImportacaoUseCase(importacaoRepository, stagingRepository, userContext);
    }

    @Test
    void shouldReviewImportacaoWhenAllTransactionsClassified() {
        UUID importacaoId = UUID.randomUUID();
        String userId = "user-1";
        Importacao importacao = new Importacao(importacaoId, userId, "arquivo.csv", Instant.now(), ImportacaoStatus.PENDENTE);
        TransacaoStaging staging = new TransacaoStaging(UUID.randomUUID(), importacaoId, userId, "Conta", new BigDecimal("50.00"), LocalDate.of(2026, 4, 12), "Conta corrente", "Alimentacao", TransacaoStagingStatus.CLASSIFICADA);

        when(userContext.getUserId()).thenReturn(userId);
        when(importacaoRepository.findById(importacaoId)).thenReturn(Optional.of(importacao));
        when(stagingRepository.findByImportacaoId(importacaoId)).thenReturn(List.of(staging));
        when(importacaoRepository.save(any(Importacao.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ImportacaoResponse response = useCase.revisar(importacaoId);

        assertNotNull(response);
        assertEquals(ImportacaoStatus.REVISAO.name(), response.getStatus());
        verify(importacaoRepository).save(any(Importacao.class));
    }
}
