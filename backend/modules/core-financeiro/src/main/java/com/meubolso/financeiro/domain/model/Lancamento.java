package com.meubolso.financeiro.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Lancamento {

    private final UUID id;
    private final String userId;
    private final String descricao;
    private final BigDecimal valor;
    private final LocalDate data;
    private final String conta;
    private final String categoria;
    private final LancamentoStatus status;

    public Lancamento(UUID id, String userId, String descricao, BigDecimal valor, LocalDate data, String conta, String categoria, LancamentoStatus status) {
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

    public Lancamento withDescricao(String descricao) {
        return new Lancamento(id, userId, descricao, valor, data, conta, categoria, status);
    }

    public Lancamento withData(LocalDate data) {
        return new Lancamento(id, userId, descricao, valor, data, conta, categoria, status);
    }

    public Lancamento withCategoria(String categoria) {
        return new Lancamento(id, userId, descricao, valor, data, conta, categoria, status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lancamento that = (Lancamento) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
