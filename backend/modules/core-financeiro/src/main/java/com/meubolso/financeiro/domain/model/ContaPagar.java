package com.meubolso.financeiro.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class ContaPagar {

    private final UUID id;
    private final String userId;
    private final String descricao;
    private final BigDecimal valor;
    private final LocalDate dataVencimento;
    private final String categoria;

    public ContaPagar(UUID id, String userId, String descricao, BigDecimal valor, LocalDate dataVencimento, String categoria) {
        if (id == null) {
            throw new IllegalArgumentException("Id de conta a pagar não pode ser nulo");
        }
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("UserId de conta a pagar não pode ser vazio");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição da conta a pagar é obrigatória");
        }
        if (valor == null || valor.signum() <= 0) {
            throw new IllegalArgumentException("Valor da conta a pagar deve ser maior que zero");
        }
        if (dataVencimento == null) {
            throw new IllegalArgumentException("Data de vencimento é obrigatória");
        }
        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("Categoria da conta a pagar é obrigatória");
        }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContaPagar that = (ContaPagar) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
