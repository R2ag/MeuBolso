package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.AtualizarLancamentoRequest;
import com.meubolso.financeiro.application.dto.LancamentoResponse;
import com.meubolso.financeiro.domain.model.Lancamento;
import com.meubolso.financeiro.domain.model.LancamentoStatus;
import com.meubolso.financeiro.domain.repository.LancamentoRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class EditarLancamentoUseCase {

    private final LancamentoRepository lancamentoRepository;
    private final UserContext userContext;

    public EditarLancamentoUseCase(LancamentoRepository lancamentoRepository, UserContext userContext) {
        this.lancamentoRepository = lancamentoRepository;
        this.userContext = userContext;
    }

    public LancamentoResponse editar(UUID id, AtualizarLancamentoRequest request) {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }

        Lancamento lancamento = lancamentoRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new IllegalArgumentException("Lançamento não encontrado"));

        if (lancamento.getStatus() == LancamentoStatus.CONFIRMADO) {
            if (!lancamento.getValor().equals(request.getValor())) {
                throw new IllegalStateException("Valor não pode ser alterado após confirmação");
            }
            if (!lancamento.getConta().equals(request.getConta())) {
                throw new IllegalStateException("Conta não pode ser alterada após confirmação");
            }
        }

        if (request.getValor() == null || request.getValor().compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Valor do lançamento deve ser diferente de zero");
        }

        Lancamento atualizado = new Lancamento(
                lancamento.getId(),
                lancamento.getUserId(),
                request.getDescricao(),
                request.getValor(),
                request.getData(),
                request.getConta(),
                request.getCategoria(),
                lancamento.getStatus()
        );

        Lancamento salvo = lancamentoRepository.save(atualizado);
        return new LancamentoResponse(
                salvo.getId(),
                salvo.getUserId(),
                salvo.getDescricao(),
                salvo.getValor(),
                salvo.getData(),
                salvo.getConta(),
                salvo.getCategoria(),
                salvo.getStatus(),
                salvo.getTipo()
        );
    }
}
