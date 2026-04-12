package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.ContaResponse;
import com.meubolso.financeiro.domain.repository.ContaRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarContasUseCase {

    private final ContaRepository contaRepository;
    private final UserContext userContext;

    public ListarContasUseCase(ContaRepository contaRepository, UserContext userContext) {
        this.contaRepository = contaRepository;
        this.userContext = userContext;
    }

    public List<ContaResponse> listar() {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }

        return contaRepository.findByUserId(userId).stream()
                .map(conta -> new ContaResponse(conta.getId(), conta.getUserId(), conta.getNome()))
                .collect(Collectors.toList());
    }
}
