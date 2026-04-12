package com.meubolso.financeiro.application.dto;

import com.meubolso.financeiro.domain.model.LancamentoStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class LancamentoResponse {

    private UUID id;
    private String userId;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private String conta;
    private String categoria;
    private LancamentoStatus status;

    public LancamentoResponse() {
    }

    public LancamentoResponse(UUID id, String userId, String descricao, BigDecimal valor, LocalDate data, String conta, String categoria, LancamentoStatus status) {
        this.id = id;
        this.userId = userId;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.conta = conta;
        this.categoria = categoria;
        this.status = status;
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

    public LocalDate getData() {
        return data;
    }

    public String getConta() {
        return conta;
    }

    public String getCategoria() {
        return categoria;
    }

    public LancamentoStatus getStatus() {
        return status;
    }
}
