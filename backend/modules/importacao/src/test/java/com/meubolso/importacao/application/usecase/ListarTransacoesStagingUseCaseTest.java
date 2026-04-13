package com.meubolso.importacao.application.usecase;

import com.meubolso.importacao.application.dto.TransacaoStagingResponse;
import com.meubolso.importacao.domain.model.TransacaoStaging;
import com.meubolso.importacao.domain.model.TransacaoStagingStatus;
import com.meubolso.importacao.domain.repository.TransacaoStagingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarTransacoesStagingUseCaseTest {

    private TransacaoStagingRepository stagingRepository;
    private ListarTransacoesStagingUseCase useCase;

    @BeforeEach
    void setUp() {
        stagingRepository = mock(TransacaoStagingRepository.class);
        useCase = new ListarTransacoesStagingUseCase(stagingRepository);
    }

    @Test
    void shouldListTransacoesStagingForImportacaoAndUser() {
        UUID importacaoId = UUID.randomUUID();
        String userId = "user-1";
        TransacaoStaging staging = new TransacaoStaging(UUID.randomUUID(), importacaoId, userId, "Pagamento mercado", new BigDecimal("100.00"), LocalDate.of(2026, 4, 12), "Conta corrente", "Alimentacao", TransacaoStagingStatus.PENDENTE);

        when(stagingRepository.findByImportacaoId(importacaoId)).thenReturn(List.of(staging));

        List<TransacaoStagingResponse> responses = useCase.listar(importacaoId, userId);

        assertEquals(1, responses.size());
        assertEquals(staging.getId(), responses.get(0).getId());
        assertEquals("Pagamento mercado", responses.get(0).getDescricao());
        assertEquals("PENDENTE", responses.get(0).getStatus());
        verify(stagingRepository).findByImportacaoId(importacaoId);
    }
}
