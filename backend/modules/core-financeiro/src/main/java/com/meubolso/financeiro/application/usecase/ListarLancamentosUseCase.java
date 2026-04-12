package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.LancamentoResponse;
import com.meubolso.financeiro.domain.repository.LancamentoRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarLancamentosUseCase {

    private final LancamentoRepository lancamentoRepository;
    private final UserContext userContext;

    public ListarLancamentosUseCase(LancamentoRepository lancamentoRepository, UserContext userContext) {
        this.lancamentoRepository = lancamentoRepository;
        this.userContext = userContext;
    }

    public List<LancamentoResponse> listar() {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }

        return lancamentoRepository.findByUserId(userId).stream()
                .map(lancamento -> new LancamentoResponse(
                        lancamento.getId(),
                        lancamento.getUserId(),
                        lancamento.getDescricao(),
                        lancamento.getValor(),
                        lancamento.getData(),
                        lancamento.getConta(),
                        lancamento.getCategoria(),
                        lancamento.getStatus()
                ))
                .collect(Collectors.toList());
    }
}
