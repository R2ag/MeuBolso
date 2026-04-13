package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.OrcamentoResponse;
import com.meubolso.financeiro.domain.repository.OrcamentoRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarOrcamentosUseCase {

    private final OrcamentoRepository orcamentoRepository;
    private final UserContext userContext;

    public ListarOrcamentosUseCase(OrcamentoRepository orcamentoRepository, UserContext userContext) {
        this.orcamentoRepository = orcamentoRepository;
        this.userContext = userContext;
    }

    public List<OrcamentoResponse> listar() {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }

        return orcamentoRepository.findByUserId(userId).stream()
                .map(orcamento -> new OrcamentoResponse(
                        orcamento.getId(),
                        orcamento.getUserId(),
                        orcamento.getCategoria(),
                        orcamento.getAno(),
                        orcamento.getMes(),
                        orcamento.getValor()))
                .collect(Collectors.toList());
    }
}
