package com.meubolso.financeiro.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public class PagarParcelaRequest {

    private UUID parcelaId;
    private String conta;
    private String categoria;
    private LocalDate dataPagamento;

    public UUID getParcelaId() {
        return parcelaId;
    }

    public void setParcelaId(UUID parcelaId) {
        this.parcelaId = parcelaId;
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

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}
