package com.meubolso.financeiro.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Divida {

    private final UUID id;
    private final String userId;
    private final String descricao;
    private final BigDecimal valorTotal;
    private final BigDecimal taxaJuros;
    private final int numeroParcelas;
    private final LocalDate dataPrimeiraParcela;
    private final String categoria;
    private final List<Parcela> parcelas;

    public Divida(UUID id, String userId, String descricao, BigDecimal valorTotal, BigDecimal taxaJuros, int numeroParcelas, LocalDate dataPrimeiraParcela, String categoria, List<Parcela> parcelas) {
        if (id == null) {
            throw new IllegalArgumentException("Id da dívida não pode ser nulo");
        }
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("UserId da dívida não pode ser vazio");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição da dívida é obrigatória");
        }
        if (valorTotal == null || valorTotal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor total da dívida deve ser maior que zero");
        }
        if (taxaJuros == null || taxaJuros.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Taxa de juros não pode ser negativa");
        }
        if (numeroParcelas <= 0) {
            throw new IllegalArgumentException("Número de parcelas deve ser maior que zero");
        }
        if (dataPrimeiraParcela == null) {
            throw new IllegalArgumentException("Data da primeira parcela é obrigatória");
        }
        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("Categoria da dívida é obrigatória");
        }
        if (parcelas == null || parcelas.isEmpty()) {
            throw new IllegalArgumentException("A dívida deve conter pelo menos uma parcela");
        }

        this.id = id;
        this.userId = userId;
        this.descricao = descricao;
        this.valorTotal = valorTotal;
        this.taxaJuros = taxaJuros;
        this.numeroParcelas = numeroParcelas;
        this.dataPrimeiraParcela = dataPrimeiraParcela;
        this.categoria = categoria;
        this.parcelas = Collections.unmodifiableList(parcelas);
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

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public BigDecimal getTaxaJuros() {
        return taxaJuros;
    }

    public int getNumeroParcelas() {
        return numeroParcelas;
    }

    public LocalDate getDataPrimeiraParcela() {
        return dataPrimeiraParcela;
    }

    public String getCategoria() {
        return categoria;
    }

    public List<Parcela> getParcelas() {
        return parcelas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Divida divida = (Divida) o;
        return id.equals(divida.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
