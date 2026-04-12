package com.meubolso.importacao.application.usecase;

import com.meubolso.importacao.application.parser.ImportacaoParser;
import com.meubolso.importacao.domain.model.Importacao;
import com.meubolso.importacao.domain.model.ImportacaoStatus;
import com.meubolso.importacao.domain.model.TransacaoStaging;
import com.meubolso.importacao.domain.repository.ImportacaoRepository;
import com.meubolso.importacao.domain.repository.TransacaoStagingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ImportarTransacoesUseCaseTest {

    private ImportacaoRepository importacaoRepository;
    private TransacaoStagingRepository stagingRepository;
    private ImportacaoParser parser;
    private ImportarTransacoesUseCase useCase;

    @BeforeEach
    void setUp() {
        importacaoRepository = mock(ImportacaoRepository.class);
        stagingRepository = mock(TransacaoStagingRepository.class);
        parser = mock(ImportacaoParser.class);
        useCase = new ImportarTransacoesUseCase(importacaoRepository, stagingRepository, parser);
    }

    @Test
    void shouldCreateImportacaoAndPersistStagingTransactions() {
        byte[] arquivo = "descricao;valor;data;conta;categoria\nPagamento mercado;100.00;2026-04-12;Conta corrente;Alimentacao".getBytes();
        String arquivoNome = "transacoes.csv";
        String userId = "user-1";
        UUID importacaoId = UUID.randomUUID();
        Importacao importacao = new Importacao(importacaoId, userId, arquivoNome, Instant.now(), ImportacaoStatus.PENDENTE);
        TransacaoStaging staging = new TransacaoStaging(UUID.randomUUID(), importacaoId, userId, "Pagamento mercado", new BigDecimal("100.00"), LocalDate.of(2026, 4, 12), "Conta corrente", "Alimentacao");

        when(parser.parse(any(), any(), eq(userId), eq(arquivoNome))).thenReturn(List.of(staging));
        when(importacaoRepository.save(any(Importacao.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(stagingRepository.save(any(TransacaoStaging.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Importacao result = useCase.importar(arquivo, arquivoNome, userId);

        assertNotNull(result);
        assertEquals(importacao.getArquivoNome(), result.getArquivoNome());
        assertEquals(ImportacaoStatus.PENDENTE, result.getStatus());
        verify(importacaoRepository).save(any(Importacao.class));
        verify(stagingRepository).save(staging);
    }
}
