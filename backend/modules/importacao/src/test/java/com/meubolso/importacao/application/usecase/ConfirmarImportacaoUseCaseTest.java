package com.meubolso.importacao.application.usecase;

import com.meubolso.financeiro.domain.model.Lancamento;
import com.meubolso.financeiro.domain.repository.LancamentoRepository;
import com.meubolso.importacao.application.dto.ImportacaoResponse;
import com.meubolso.importacao.domain.model.Importacao;
import com.meubolso.importacao.domain.model.ImportacaoStatus;
import com.meubolso.importacao.domain.model.TransacaoStaging;
import com.meubolso.importacao.domain.repository.ImportacaoRepository;
import com.meubolso.importacao.domain.repository.TransacaoStagingRepository;
import com.meubolso.shared.security.UserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConfirmarImportacaoUseCaseTest {

    private ImportacaoRepository importacaoRepository;
    private TransacaoStagingRepository stagingRepository;
    private LancamentoRepository lancamentoRepository;
    private UserContext userContext;
    private ConfirmarImportacaoUseCase useCase;

    @BeforeEach
    void setUp() {
        importacaoRepository = mock(ImportacaoRepository.class);
        stagingRepository = mock(TransacaoStagingRepository.class);
        lancamentoRepository = mock(LancamentoRepository.class);
        userContext = mock(UserContext.class);
        useCase = new ConfirmarImportacaoUseCase(importacaoRepository, stagingRepository, lancamentoRepository);
    }

    @Test
    void shouldConfirmImportacaoAndSaveLancamentos() {
        UUID importacaoId = UUID.randomUUID();
        String userId = "user-1";
        Importacao importacao = new Importacao(importacaoId, userId, "arquivo.csv", Instant.now(), ImportacaoStatus.REVISAO);
        TransacaoStaging staging1 = new TransacaoStaging(UUID.randomUUID(), importacaoId, userId, "Compra loja", new BigDecimal("150.00"), LocalDate.of(2026, 4, 10), "Conta corrente", "Compras");
        TransacaoStaging staging2 = new TransacaoStaging(UUID.randomUUID(), importacaoId, userId, "Salario", new BigDecimal("2000.00"), LocalDate.of(2026, 4, 1), "Conta poupança", "Receita");

        when(importacaoRepository.findById(importacaoId)).thenReturn(Optional.of(importacao));
        when(stagingRepository.findByImportacaoId(importacaoId)).thenReturn(List.of(staging1, staging2));

        ImportacaoResponse response = useCase.confirmar(importacaoId, userId);

        assertNotNull(response);
        assertEquals(importacaoId, response.getId());
        assertEquals("CONFIRMADA", response.getStatus());

        ArgumentCaptor<Lancamento> lancamentoCaptor = ArgumentCaptor.forClass(Lancamento.class);
        verify(lancamentoRepository, times(2)).save(lancamentoCaptor.capture());
        List<Lancamento> savedLancamentos = lancamentoCaptor.getAllValues();

        assertTrue(savedLancamentos.stream().anyMatch(l -> l.getDescricao().equals("Compra loja") && l.getValor().equals(new BigDecimal("150.00"))));
        assertTrue(savedLancamentos.stream().anyMatch(l -> l.getDescricao().equals("Salario") && l.getValor().equals(new BigDecimal("2000.00"))));
        verify(importacaoRepository).save(argThat(savedImportacao -> savedImportacao.getStatus() == ImportacaoStatus.CONFIRMADA));
    }
}
