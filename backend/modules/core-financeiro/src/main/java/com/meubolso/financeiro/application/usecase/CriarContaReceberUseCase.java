package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.ContaReceberRequest;
import com.meubolso.financeiro.application.dto.ContaReceberResponse;
import com.meubolso.financeiro.domain.model.ContaReceber;
import com.meubolso.financeiro.domain.repository.ContaReceberRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CriarContaReceberUseCase {

    private final ContaReceberRepository contaReceberRepository;
    private final UserContext userContext;

    public CriarContaReceberUseCase(ContaReceberRepository contaReceberRepository, UserContext userContext) {
        this.contaReceberRepository = contaReceberRepository;
        this.userContext = userContext;
    }

    public ContaReceberResponse criar(ContaReceberRequest request) {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }

        ContaReceber contaReceber = new ContaReceber(
                UUID.randomUUID(),
                userId,
                request.getDescricao(),
                request.getValor(),
                request.getDataVencimento(),
                request.getCategoria()
        );

        ContaReceber salvo = contaReceberRepository.save(contaReceber);
        return new ContaReceberResponse(
                salvo.getId(),
                salvo.getUserId(),
                salvo.getDescricao(),
                salvo.getValor(),
                salvo.getDataVencimento(),
                salvo.getCategoria()
        );
    }
}
