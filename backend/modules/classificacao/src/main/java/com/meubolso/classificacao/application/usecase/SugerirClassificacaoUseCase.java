package com.meubolso.classificacao.application.usecase;

import com.meubolso.classificacao.application.dto.ClassificacaoRequest;
import com.meubolso.classificacao.application.dto.ClassificacaoResponse;
import com.meubolso.classificacao.domain.model.ClassificacaoSugestao;
import com.meubolso.classificacao.domain.service.ClassificacaoService;
import org.springframework.stereotype.Service;

@Service
public class SugerirClassificacaoUseCase {

    private final ClassificacaoService classificacaoService;

    public SugerirClassificacaoUseCase(ClassificacaoService classificacaoService) {
        this.classificacaoService = classificacaoService;
    }

    public ClassificacaoResponse sugerir(ClassificacaoRequest request) {
        ClassificacaoSugestao sugestao = classificacaoService.sugerir(request.getUserId(), request.getDescricao(), request.getContraparte());
        return new ClassificacaoResponse(sugestao.getCategoria(), sugestao.getContraparte(), sugestao.getConfianca(), sugestao.getFonte());
    }
}
