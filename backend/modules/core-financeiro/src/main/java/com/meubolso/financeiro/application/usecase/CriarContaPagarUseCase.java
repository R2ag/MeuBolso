package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.ContaPagarRequest;
import com.meubolso.financeiro.application.dto.ContaPagarResponse;
import com.meubolso.financeiro.domain.model.ContaPagar;
import com.meubolso.financeiro.domain.repository.ContaPagarRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CriarContaPagarUseCase {

    private final ContaPagarRepository contaPagarRepository;
    private final UserContext userContext;

    public CriarContaPagarUseCase(ContaPagarRepository contaPagarRepository, UserContext userContext) {
        this.contaPagarRepository = contaPagarRepository;
        this.userContext = userContext;
    }

    public ContaPagarResponse criar(ContaPagarRequest request) {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }

        ContaPagar contaPagar = new ContaPagar(
                UUID.randomUUID(),
                userId,
                request.getDescricao(),
                request.getValor(),
                request.getDataVencimento(),
                request.getCategoria()
        );

        ContaPagar salvo = contaPagarRepository.save(contaPagar);
        return new ContaPagarResponse(
                salvo.getId(),
                salvo.getUserId(),
                salvo.getDescricao(),
                salvo.getValor(),
                salvo.getDataVencimento(),
                salvo.getCategoria()
        );
    }
}
