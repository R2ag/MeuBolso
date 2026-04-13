package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.ContaReceberResponse;
import com.meubolso.financeiro.domain.repository.ContaReceberRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarContasReceberUseCase {

    private final ContaReceberRepository contaReceberRepository;
    private final UserContext userContext;

    public ListarContasReceberUseCase(ContaReceberRepository contaReceberRepository, UserContext userContext) {
        this.contaReceberRepository = contaReceberRepository;
        this.userContext = userContext;
    }

    public List<ContaReceberResponse> listar() {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }

        return contaReceberRepository.findByUserId(userId).stream()
                .map(contaReceber -> new ContaReceberResponse(
                        contaReceber.getId(),
                        contaReceber.getUserId(),
                        contaReceber.getDescricao(),
                        contaReceber.getValor(),
                        contaReceber.getDataVencimento(),
                        contaReceber.getCategoria()))
                .collect(Collectors.toList());
    }
}
