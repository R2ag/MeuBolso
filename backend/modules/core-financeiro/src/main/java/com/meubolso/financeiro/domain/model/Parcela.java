package com.meubolso.financeiro.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Parcela {

    private final UUID id;
    private final String userId;
    private final UUID dividaId;
    private final int numero;
    private final BigDecimal valor;
    private final LocalDate dataVencimento;
    private final ParcelaStatus status;
    private final UUID lancamentoId;

    public Parcela(UUID id, String userId, UUID dividaId, int numero, BigDecimal valor, LocalDate dataVencimento, ParcelaStatus status, UUID lancamentoId) {
        if (id == null) {
            throw new IllegalArgumentException("Id da parcela não pode ser nulo");
        }
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("UserId da parcela não pode ser vazio");
        }
        if (dividaId == null) {
            throw new IllegalArgumentException("Id da dívida não pode ser nulo");
        }
        if (numero <= 0) {
            throw new IllegalArgumentException("Número da parcela deve ser maior que zero");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da parcela deve ser maior que zero");
        }
        if (dataVencimento == null) {
            throw new IllegalArgumentException("Data de vencimento da parcela é obrigatória");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status da parcela é obrigatório");
        }

        this.id = id;
        this.userId = userId;
        this.dividaId = dividaId;
        this.numero = numero;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.status = status;
        this.lancamentoId = lancamentoId;
    }

    public UUID getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public UUID getDividaId() {
        return dividaId;
    }

    public int getNumero() {
        return numero;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public ParcelaStatus getStatus() {
        return status;
    }

    public UUID getLancamentoId() {
        return lancamentoId;
    }

    public boolean isPago() {
        return status == ParcelaStatus.PAGA;
    }

    public Parcela pagar(UUID lancamentoId) {
        if (lancamentoId == null) {
            throw new IllegalArgumentException("Id do lançamento é obrigatório ao pagar parcela");
        }
        if (isPago()) {
            throw new IllegalStateException("Parcela já está paga");
        }
        return new Parcela(id, userId, dividaId, numero, valor, dataVencimento, ParcelaStatus.PAGA, lancamentoId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parcela parcela = (Parcela) o;
        return id.equals(parcela.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
