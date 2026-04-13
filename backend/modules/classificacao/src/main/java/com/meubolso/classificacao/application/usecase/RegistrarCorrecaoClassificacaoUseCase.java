package com.meubolso.classificacao.application.usecase;

import com.meubolso.classificacao.application.dto.RegistrarCorrecaoRequest;
import com.meubolso.classificacao.domain.model.ClassificacaoHistorico;
import com.meubolso.classificacao.domain.repository.ClassificacaoHistoricoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RegistrarCorrecaoClassificacaoUseCase {

    private final ClassificacaoHistoricoRepository historicoRepository;

    public RegistrarCorrecaoClassificacaoUseCase(ClassificacaoHistoricoRepository historicoRepository) {
        this.historicoRepository = historicoRepository;
    }

    public ClassificacaoHistorico registrar(RegistrarCorrecaoRequest request) {
        ClassificacaoHistorico historico = new ClassificacaoHistorico(
                UUID.randomUUID(),
                request.getUserId(),
                request.getDescricao(),
                request.getContraparte(),
                request.getCategoriaCorrigida(),
                LocalDateTime.now());
        return historicoRepository.save(historico);
    }
}
