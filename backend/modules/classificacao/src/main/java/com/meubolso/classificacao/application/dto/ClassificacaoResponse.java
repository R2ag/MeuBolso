package com.meubolso.classificacao.application.dto;

public class ClassificacaoResponse {

    private String categoria;
    private String contraparte;
    private double confianca;
    private String fonte;

    public ClassificacaoResponse() {
    }

    public ClassificacaoResponse(String categoria, String contraparte, double confianca, String fonte) {
        this.categoria = categoria;
        this.contraparte = contraparte;
        this.confianca = confianca;
        this.fonte = fonte;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getContraparte() {
        return contraparte;
    }

    public double getConfianca() {
        return confianca;
    }

    public String getFonte() {
        return fonte;
    }
}
