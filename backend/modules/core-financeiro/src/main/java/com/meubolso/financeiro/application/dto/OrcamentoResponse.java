package com.meubolso.financeiro.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class OrcamentoResponse {

    private UUID id;
    private String userId;
    private String categoria;
    private int ano;
    private int mes;
    private BigDecimal valor;

    public OrcamentoResponse() {
    }

    public OrcamentoResponse(UUID id, String userId, String categoria, int ano, int mes, BigDecimal valor) {
        this.id = id;
        this.userId = userId;
        this.categoria = categoria;
        this.ano = ano;
        this.mes = mes;
        this.valor = valor;
    }

    public UUID getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getAno() {
        return ano;
    }

    public int getMes() {
        return mes;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
