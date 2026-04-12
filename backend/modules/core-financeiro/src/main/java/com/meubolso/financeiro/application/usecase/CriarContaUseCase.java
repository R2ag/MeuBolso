package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.ContaRequest;
import com.meubolso.financeiro.application.dto.ContaResponse;
import com.meubolso.financeiro.domain.model.Conta;
import com.meubolso.financeiro.domain.repository.ContaRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CriarContaUseCase {

    private final ContaRepository contaRepository;
    private final UserContext userContext;

    public CriarContaUseCase(ContaRepository contaRepository, UserContext userContext) {
        this.contaRepository = contaRepository;
        this.userContext = userContext;
    }

    public ContaResponse criar(ContaRequest request) {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }

        Conta conta = new Conta(UUID.randomUUID(), userId, request.getNome());
        Conta salvo = contaRepository.save(conta);
        return new ContaResponse(salvo.getId(), salvo.getUserId(), salvo.getNome());
    }
}
