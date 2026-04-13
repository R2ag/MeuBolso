package com.meubolso.classificacao.domain.model;

public class ClassificacaoSugestao {

    private final String categoria;
    private final String contraparte;
    private final double confianca;
    private final String fonte;

    public ClassificacaoSugestao(String categoria, String contraparte, double confianca, String fonte) {
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
