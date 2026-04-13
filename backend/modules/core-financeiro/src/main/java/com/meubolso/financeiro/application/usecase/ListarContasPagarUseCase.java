package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.ContaPagarResponse;
import com.meubolso.financeiro.domain.repository.ContaPagarRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarContasPagarUseCase {

    private final ContaPagarRepository contaPagarRepository;
    private final UserContext userContext;

    public ListarContasPagarUseCase(ContaPagarRepository contaPagarRepository, UserContext userContext) {
        this.contaPagarRepository = contaPagarRepository;
        this.userContext = userContext;
    }

    public List<ContaPagarResponse> listar() {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }

        return contaPagarRepository.findByUserId(userId).stream()
                .map(contaPagar -> new ContaPagarResponse(
                        contaPagar.getId(),
                        contaPagar.getUserId(),
                        contaPagar.getDescricao(),
                        contaPagar.getValor(),
                        contaPagar.getDataVencimento(),
                        contaPagar.getCategoria()))
                .collect(Collectors.toList());
    }
}
