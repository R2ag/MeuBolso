package com.meubolso.classificacao.application.dto;

import jakarta.validation.constraints.NotBlank;

public class RegistrarCorrecaoRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String descricao;

    private String contraparte;

    @NotBlank
    private String categoriaCorrigida;

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

    public String getCategoriaCorrigida() {
        return categoriaCorrigida;
    }

    public void setCategoriaCorrigida(String categoriaCorrigida) {
        this.categoriaCorrigida = categoriaCorrigida;
    }
}
