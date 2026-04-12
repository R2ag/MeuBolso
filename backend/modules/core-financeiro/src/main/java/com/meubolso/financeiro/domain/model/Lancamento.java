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
        if (id == null) {
            throw new IllegalArgumentException("Id do lançamento não pode ser nulo");
        }
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("UserId do lançamento não pode ser vazio");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição do lançamento é obrigatória");
        }
        if (valor == null) {
            throw new IllegalArgumentException("Valor do lançamento é obrigatório");
        }
        if (valor.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Valor do lançamento deve ser diferente de zero");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data do lançamento é obrigatória");
        }
        if (conta == null || conta.isBlank()) {
            throw new IllegalArgumentException("Conta do lançamento é obrigatória");
        }
        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("Categoria do lançamento é obrigatória");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status do lançamento é obrigatório");
        }

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

    public LancamentoTipo getTipo() {
        return valor.compareTo(BigDecimal.ZERO) > 0 ? LancamentoTipo.RECEITA : LancamentoTipo.DESPESA;
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
