package com.meubolso.classificacao.domain.service;

import com.meubolso.classificacao.domain.model.ClassificacaoHistorico;
import com.meubolso.classificacao.domain.model.ClassificacaoSugestao;
import com.meubolso.classificacao.domain.repository.ClassificacaoHistoricoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClassificacaoServiceTest {

    private ClassificacaoHistoricoRepository historicoRepository;
    private ClassificacaoService classificacaoService;

    @BeforeEach
    void setUp() {
        historicoRepository = mock(ClassificacaoHistoricoRepository.class);
        classificacaoService = new ClassificacaoService(historicoRepository);
    }

    @Test
    void shouldApplyExplicitRuleWhenDescriptionMatchesKeyword() {
        String userId = "user-1";
        when(historicoRepository.findByUserId(userId)).thenReturn(List.of());

        ClassificacaoSugestao sugestao = classificacaoService.sugerir(userId, "Compra no supermercado", "Extra");

        assertEquals("Alimentação", sugestao.getCategoria());
        assertEquals(0.95, sugestao.getConfianca());
        assertEquals("regra", sugestao.getFonte());
    }

    @Test
    void shouldUseExactHistoryWhenNoRuleApplies() {
        String userId = "user-2";
        ClassificacaoHistorico historico = new ClassificacaoHistorico(UUID.randomUUID(), userId, "Jantar restaurante", "Itau", "Alimentação", LocalDateTime.now());
        when(historicoRepository.findByUserId(userId)).thenReturn(List.of(historico));

        ClassificacaoSugestao sugestao = classificacaoService.sugerir(userId, "Jantar restaurante", "Itau");

        assertEquals("Alimentação", sugestao.getCategoria());
        assertEquals(0.90, sugestao.getConfianca());
        assertEquals("histórico-exato", sugestao.getFonte());
    }

    @Test
    void shouldUseSimilarityWhenNoRuleOrExactHistoryMatches() {
        String userId = "user-3";
        ClassificacaoHistorico historico = new ClassificacaoHistorico(UUID.randomUUID(), userId, "Passagem de ônibus", "Empresa de ônibus", "Transporte", LocalDateTime.now());
        when(historicoRepository.findByUserId(userId)).thenReturn(List.of(historico));

        ClassificacaoSugestao sugestao = classificacaoService.sugerir(userId, "Viagem de ônibus", "Empresa de ônibus");

        assertEquals("Transporte", sugestao.getCategoria());
        assertEquals(0.75, sugestao.getConfianca());
        assertEquals("similaridade", sugestao.getFonte());
    }

    @Test
    void shouldFallbackWhenNoRuleHistoryOrSimilarityExists() {
        String userId = "user-4";
        when(historicoRepository.findByUserId(userId)).thenReturn(List.of());

        ClassificacaoSugestao sugestao = classificacaoService.sugerir(userId, "Pagamento desconhecido", "Fornecedor X");

        assertEquals("Sem categoria", sugestao.getCategoria());
        assertEquals(0.50, sugestao.getConfianca());
        assertEquals("fallback", sugestao.getFonte());
    }
}
