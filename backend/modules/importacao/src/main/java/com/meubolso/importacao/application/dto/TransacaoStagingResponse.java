package com.meubolso.importacao.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class TransacaoStagingResponse {

    private UUID id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private String conta;
    private String categoria;
    private String status;
    private String categoriaSugestao;
    private Double confiancaSugestao;
    private String fonteSugestao;

    public TransacaoStagingResponse() {
    }

    public TransacaoStagingResponse(UUID id, String descricao, BigDecimal valor, LocalDate data, String conta, String categoria, String status) {
        this(id, descricao, valor, data, conta, categoria, status, null, null, null);
    }

    public TransacaoStagingResponse(UUID id, String descricao, BigDecimal valor, LocalDate data, String conta, String categoria, String status,
                                    String categoriaSugestao, Double confiancaSugestao, String fonteSugestao) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.conta = conta;
        this.categoria = categoria;
        this.status = status;
        this.categoriaSugestao = categoriaSugestao;
        this.confiancaSugestao = confiancaSugestao;
        this.fonteSugestao = fonteSugestao;
    }

    public String getStatus() {
        return status;
    }

    public UUID getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public String getConta() {
        return conta;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getCategoriaSugestao() {
        return categoriaSugestao;
    }

    public Double getConfiancaSugestao() {
        return confiancaSugestao;
    }

    public String getFonteSugestao() {
        return fonteSugestao;
    }
}
