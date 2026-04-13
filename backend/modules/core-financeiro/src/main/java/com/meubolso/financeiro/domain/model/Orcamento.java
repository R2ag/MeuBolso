package com.meubolso.financeiro.domain.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Orcamento {

    private final UUID id;
    private final String userId;
    private final String categoria;
    private final int ano;
    private final int mes;
    private final BigDecimal valor;

    public Orcamento(UUID id, String userId, String categoria, int ano, int mes, BigDecimal valor) {
        if (id == null) {
            throw new IllegalArgumentException("Id de orçamento não pode ser nulo");
        }
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("UserId de orçamento não pode ser vazio");
        }
        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("Categoria de orçamento é obrigatória");
        }
        if (ano < 1900 || ano > 2100) {
            throw new IllegalArgumentException("Ano de orçamento inválido");
        }
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("Mês de orçamento inválido");
        }
        if (valor == null) {
            throw new IllegalArgumentException("Valor do orçamento é obrigatório");
        }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orcamento orcamento = (Orcamento) o;
        return id.equals(orcamento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
