package com.meubolso.financeiro.application.dto;

import java.math.BigDecimal;

public class ProjecaoSaldoResponse {

    private BigDecimal saldoAtual;
    private BigDecimal entradasFuturas;
    private BigDecimal saidasFuturas;
    private BigDecimal saldoProjetado;

    public ProjecaoSaldoResponse() {
    }

    public ProjecaoSaldoResponse(BigDecimal saldoAtual, BigDecimal entradasFuturas, BigDecimal saidasFuturas, BigDecimal saldoProjetado) {
        this.saldoAtual = saldoAtual;
        this.entradasFuturas = entradasFuturas;
        this.saidasFuturas = saidasFuturas;
        this.saldoProjetado = saldoProjetado;
    }

    public BigDecimal getSaldoAtual() {
        return saldoAtual;
    }

    public BigDecimal getEntradasFuturas() {
        return entradasFuturas;
    }

    public BigDecimal getSaidasFuturas() {
        return saidasFuturas;
    }

    public BigDecimal getSaldoProjetado() {
        return saldoProjetado;
    }
}
