package com.meubolso.importacao.application.usecase;

import com.meubolso.classificacao.domain.model.ClassificacaoSugestao;
import com.meubolso.classificacao.domain.service.ClassificacaoService;
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
    private ClassificacaoService classificacaoService;
    private ListarTransacoesStagingUseCase useCase;

    @BeforeEach
    void setUp() {
        stagingRepository = mock(TransacaoStagingRepository.class);
        classificacaoService = mock(ClassificacaoService.class);
        useCase = new ListarTransacoesStagingUseCase(stagingRepository, classificacaoService);
    }

    @Test
    void shouldListTransacoesStagingForImportacaoAndUserWithSuggestionForPendingTransactions() {
        UUID importacaoId = UUID.randomUUID();
        String userId = "user-1";
        TransacaoStaging staging = new TransacaoStaging(UUID.randomUUID(), importacaoId, userId, "Pagamento mercado", new BigDecimal("100.00"), LocalDate.of(2026, 4, 12), "Conta corrente", "Sem categoria", TransacaoStagingStatus.PENDENTE);
        ClassificacaoSugestao sugestao = new ClassificacaoSugestao("Alimentação", "Conta corrente", 0.95, "regra");

        when(stagingRepository.findByImportacaoId(importacaoId)).thenReturn(List.of(staging));
        when(classificacaoService.sugerir(userId, staging.getDescricao(), staging.getConta())).thenReturn(sugestao);

        List<TransacaoStagingResponse> responses = useCase.listar(importacaoId, userId);

        assertEquals(1, responses.size());
        TransacaoStagingResponse response = responses.get(0);
        assertEquals(staging.getId(), response.getId());
        assertEquals("Pagamento mercado", response.getDescricao());
        assertEquals("PENDENTE", response.getStatus());
        assertEquals("Alimentação", response.getCategoriaSugestao());
        assertEquals(0.95, response.getConfiancaSugestao());
        assertEquals("regra", response.getFonteSugestao());
        verify(stagingRepository).findByImportacaoId(importacaoId);
        verify(classificacaoService).sugerir(userId, staging.getDescricao(), staging.getConta());
    }
}
