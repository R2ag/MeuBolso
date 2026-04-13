package com.meubolso.financeiro.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class OrcamentoResumoResponse {

    private UUID id;
    private String categoria;
    private int ano;
    private int mes;
    private BigDecimal orcamentoPlanejado;
    private BigDecimal realizado;
    private BigDecimal saldoRestante;

    public OrcamentoResumoResponse() {
    }

    public OrcamentoResumoResponse(UUID id, String categoria, int ano, int mes, BigDecimal orcamentoPlanejado, BigDecimal realizado, BigDecimal saldoRestante) {
        this.id = id;
        this.categoria = categoria;
        this.ano = ano;
        this.mes = mes;
        this.orcamentoPlanejado = orcamentoPlanejado;
        this.realizado = realizado;
        this.saldoRestante = saldoRestante;
    }

    public UUID getId() {
        return id;
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

    public BigDecimal getOrcamentoPlanejado() {
        return orcamentoPlanejado;
    }

    public BigDecimal getRealizado() {
        return realizado;
    }

    public BigDecimal getSaldoRestante() {
        return saldoRestante;
    }
}
