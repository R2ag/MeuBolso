package com.meubolso.classificacao.application.dto;

import jakarta.validation.constraints.NotBlank;

public class ClassificacaoRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String descricao;

    private String contraparte;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getContraparte() {
        return contraparte;
    }

    public void setContraparte(String contraparte) {
        this.contraparte = contraparte;
    }
}
