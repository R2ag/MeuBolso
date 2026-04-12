package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.LancamentoRequest;
import com.meubolso.financeiro.application.dto.LancamentoResponse;
import com.meubolso.financeiro.domain.model.Lancamento;
import com.meubolso.financeiro.domain.model.LancamentoStatus;
import com.meubolso.financeiro.domain.repository.LancamentoRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CriarLancamentoUseCase {

    private final LancamentoRepository lancamentoRepository;
    private final UserContext userContext;

    public CriarLancamentoUseCase(LancamentoRepository lancamentoRepository, UserContext userContext) {
        this.lancamentoRepository = lancamentoRepository;
        this.userContext = userContext;
    }

    public LancamentoResponse criar(LancamentoRequest request) {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }

        if (request.getValor() == null || request.getValor().compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Valor do lançamento deve ser diferente de zero");
        }

        Lancamento lancamento = new Lancamento(
                UUID.randomUUID(),
                userId,
                request.getDescricao(),
                request.getValor(),
                request.getData(),
                request.getConta(),
                request.getCategoria(),
                LancamentoStatus.CONFIRMADO
        );

        Lancamento salvo = lancamentoRepository.save(lancamento);
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
