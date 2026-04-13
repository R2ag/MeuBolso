package com.meubolso.financeiro.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ContaReceberResponse {

    private UUID id;
    private String userId;
    private String descricao;
    private BigDecimal valor;
    private LocalDate dataVencimento;
    private String categoria;

    public ContaReceberResponse() {
    }

    public ContaReceberResponse(UUID id, String userId, String descricao, BigDecimal valor, LocalDate dataVencimento, String categoria) {
        this.id = id;
        this.userId = userId;
        this.descricao = descricao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.categoria = categoria;
    }

    public UUID getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public String getCategoria() {
        return categoria;
    }
}
