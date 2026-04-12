package com.meubolso.importacao.application.dto;

public class AtualizarTransacaoStagingRequest {

    private String descricao;
    private String conta;
    private String categoria;

    public AtualizarTransacaoStagingRequest() {
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
