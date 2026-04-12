package com.meubolso.importacao.application.usecase;

import com.meubolso.financeiro.domain.model.Lancamento;
import com.meubolso.financeiro.domain.model.LancamentoStatus;
import com.meubolso.financeiro.domain.repository.LancamentoRepository;
import com.meubolso.importacao.application.dto.ImportacaoResponse;
import com.meubolso.importacao.domain.model.Importacao;
import com.meubolso.importacao.domain.model.ImportacaoStatus;
import com.meubolso.importacao.domain.model.TransacaoStaging;
import com.meubolso.importacao.domain.repository.ImportacaoRepository;
import com.meubolso.importacao.domain.repository.TransacaoStagingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConfirmarImportacaoUseCase {

    private final ImportacaoRepository importacaoRepository;
    private final TransacaoStagingRepository stagingRepository;
    private final LancamentoRepository lancamentoRepository;

    public ConfirmarImportacaoUseCase(ImportacaoRepository importacaoRepository,
                                      TransacaoStagingRepository stagingRepository,
                                      LancamentoRepository lancamentoRepository) {
        this.importacaoRepository = importacaoRepository;
        this.stagingRepository = stagingRepository;
        this.lancamentoRepository = lancamentoRepository;
    }

    public ImportacaoResponse confirmar(UUID importacaoId, String userId) {
        if (importacaoId == null) {
            throw new IllegalArgumentException("Id da importação é obrigatório");
        }
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("Usuário não autenticado");
        }

        Importacao importacao = importacaoRepository.findById(importacaoId)
                .orElseThrow(() -> new IllegalArgumentException("Importação não encontrada"));

        if (!userId.equals(importacao.getUserId())) {
            throw new IllegalArgumentException("Acesso negado à importação");
        }

        if (importacao.getStatus() == ImportacaoStatus.CONFIRMADA) {
            throw new IllegalStateException("Importação já foi confirmada");
        }

        if (importacao.getStatus() != ImportacaoStatus.REVISAO) {
            throw new IllegalStateException("Importação deve estar em revisão antes de confirmar");
        }

        List<TransacaoStaging> transacoes = stagingRepository.findByImportacaoId(importacaoId).stream()
                .filter(transacao -> userId.equals(transacao.getUserId()))
                .collect(Collectors.toList());

        if (transacoes.isEmpty()) {
            throw new IllegalStateException("Não há transações em staging para confirmar");
        }

        transacoes.stream()
                .map(transacao -> new Lancamento(
                        UUID.randomUUID(),
                        userId,
                        transacao.getDescricao(),
                        transacao.getValor(),
                        transacao.getData(),
                        transacao.getConta(),
                        transacao.getCategoria(),
                        LancamentoStatus.CONFIRMADO
                ))
                .forEach(lancamentoRepository::save);

        Importacao confirmada = importacao.confirmar();
        importacaoRepository.save(confirmada);

        return new ImportacaoResponse(
                confirmada.getId(),
                confirmada.getArquivoNome(),
                confirmada.getCriadoEm(),
                confirmada.getStatus().name()
        );
    }
}
