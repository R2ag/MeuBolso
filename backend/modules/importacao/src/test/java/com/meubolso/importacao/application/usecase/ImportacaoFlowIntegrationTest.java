package com.meubolso.importacao.application.usecase;

import com.meubolso.financeiro.infrastructure.persistence.InMemoryLancamentoRepository;
import com.meubolso.importacao.application.dto.AtualizarTransacaoStagingRequest;
import com.meubolso.importacao.application.dto.TransacaoStagingResponse;
import com.meubolso.importacao.application.parser.ImportacaoParser;
import com.meubolso.importacao.domain.model.Importacao;
import com.meubolso.importacao.domain.model.ImportacaoStatus;
import com.meubolso.importacao.domain.model.TransacaoStaging;
import com.meubolso.importacao.domain.model.TransacaoStagingStatus;
import com.meubolso.classificacao.application.usecase.RegistrarCorrecaoClassificacaoUseCase;
import com.meubolso.classificacao.domain.service.ClassificacaoService;
import com.meubolso.classificacao.infrastructure.persistence.InMemoryClassificacaoHistoricoRepository;
import com.meubolso.importacao.infrastructure.persistence.InMemoryImportacaoRepository;
import com.meubolso.importacao.infrastructure.persistence.InMemoryTransacaoStagingRepository;
import com.meubolso.shared.security.UserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ImportacaoFlowIntegrationTest {

    private UserContext userContext;
    private InMemoryImportacaoRepository importacaoRepository;
    private InMemoryTransacaoStagingRepository stagingRepository;
    private InMemoryLancamentoRepository lancamentoRepository;
    private ImportarTransacoesUseCase importarTransacoesUseCase;
    private ListarTransacoesStagingUseCase listarTransacoesStagingUseCase;
    private ClassificarTransacaoStagingUseCase classificarTransacaoStagingUseCase;
    private RevisarImportacaoUseCase revisarImportacaoUseCase;
    private ConfirmarImportacaoUseCase confirmarImportacaoUseCase;

    @BeforeEach
    void setUp() {
        userContext = new UserContext();
        importacaoRepository = new InMemoryImportacaoRepository();
        stagingRepository = new InMemoryTransacaoStagingRepository();
        lancamentoRepository = new InMemoryLancamentoRepository();
        ImportacaoParser parser = new ImportacaoParser();

        importarTransacoesUseCase = new ImportarTransacoesUseCase(importacaoRepository, stagingRepository, parser);
        ClassificacaoService classificacaoService = new ClassificacaoService(new InMemoryClassificacaoHistoricoRepository());
        listarTransacoesStagingUseCase = new ListarTransacoesStagingUseCase(stagingRepository, classificacaoService);
        RegistrarCorrecaoClassificacaoUseCase registrarCorrecaoClassificacaoUseCase = new RegistrarCorrecaoClassificacaoUseCase(new InMemoryClassificacaoHistoricoRepository());
        classificarTransacaoStagingUseCase = new ClassificarTransacaoStagingUseCase(stagingRepository, userContext, registrarCorrecaoClassificacaoUseCase);
        revisarImportacaoUseCase = new RevisarImportacaoUseCase(importacaoRepository, stagingRepository, userContext);
        confirmarImportacaoUseCase = new ConfirmarImportacaoUseCase(importacaoRepository, stagingRepository, lancamentoRepository);
    }

    @Test
    void shouldRunFullImportacaoFlowAndPersistLancamentos() {
        String userId = "user-1";
        userContext.setUserId(userId);

        String csv = "descricao;valor;data;conta;categoria\n"
                + "Pagamento mercado;100.00;2026-04-12;Conta corrente;Alimentacao\n"
                + "Pagamento mercado;100.00;2026-04-12;Conta corrente;Alimentacao\n"
                + "Salario;2000.00;2026-04-10;Conta poupança;Receita";

        Importacao importacao = importarTransacoesUseCase.importar(csv.getBytes(), "transacoes.csv", userId);

        assertNotNull(importacao);
        assertEquals(ImportacaoStatus.PENDENTE, importacao.getStatus());

        List<TransacaoStagingResponse> stagingResponses = listarTransacoesStagingUseCase.listar(importacao.getId(), userId);
        assertEquals(2, stagingResponses.size());
        assertTrue(stagingResponses.stream().anyMatch(response -> response.getDescricao().equals("Pagamento mercado")));
        assertTrue(stagingResponses.stream().anyMatch(response -> response.getDescricao().equals("Salario")));

        List<TransacaoStaging> transacoes = stagingRepository.findByImportacaoId(importacao.getId());
        assertEquals(2, transacoes.size());
        for (TransacaoStaging transacao : transacoes) {
            AtualizarTransacaoStagingRequest request = new AtualizarTransacaoStagingRequest();
            request.setConta(transacao.getConta());
            request.setCategoria(transacao.getCategoria());
            TransacaoStagingResponse classified = classificarTransacaoStagingUseCase.classificar(importacao.getId(), transacao.getId(), request);
            assertEquals(TransacaoStagingStatus.CLASSIFICADA.name(), classified.getStatus());
        }

        var revisão = revisarImportacaoUseCase.revisar(importacao.getId());
        assertEquals(ImportacaoStatus.REVISAO.name(), revisão.getStatus());

        var confirmacao = confirmarImportacaoUseCase.confirmar(importacao.getId(), userId);
        assertEquals(ImportacaoStatus.CONFIRMADA.name(), confirmacao.getStatus());

        List<com.meubolso.financeiro.domain.model.Lancamento> lancamentos = lancamentoRepository.findByUserId(userId);
        assertEquals(2, lancamentos.size());
        assertTrue(lancamentos.stream().anyMatch(lancamento -> lancamento.getDescricao().equals("Pagamento mercado") && lancamento.getValor().compareTo(new BigDecimal("100.00")) == 0));
        assertTrue(lancamentos.stream().anyMatch(lancamento -> lancamento.getDescricao().equals("Salario") && lancamento.getValor().compareTo(new BigDecimal("2000.00")) == 0));
    }
}
