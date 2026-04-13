package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.OrcamentoRequest;
import com.meubolso.financeiro.application.dto.OrcamentoResponse;
import com.meubolso.financeiro.domain.model.Orcamento;
import com.meubolso.financeiro.domain.repository.OrcamentoRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CriarOrcamentoUseCase {

    private final OrcamentoRepository orcamentoRepository;
    private final UserContext userContext;

    public CriarOrcamentoUseCase(OrcamentoRepository orcamentoRepository, UserContext userContext) {
        this.orcamentoRepository = orcamentoRepository;
        this.userContext = userContext;
    }

    public OrcamentoResponse criar(OrcamentoRequest request) {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }

        Orcamento orcamento = new Orcamento(
                UUID.randomUUID(),
                userId,
                request.getCategoria(),
                request.getAno(),
                request.getMes(),
                request.getValor()
        );
        Orcamento salvo = orcamentoRepository.save(orcamento);
        return new OrcamentoResponse(salvo.getId(), salvo.getUserId(), salvo.getCategoria(), salvo.getAno(), salvo.getMes(), salvo.getValor());
    }
}
