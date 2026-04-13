package com.meubolso.financeiro.application.dto;

import com.meubolso.financeiro.domain.model.ParcelaStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ParcelaResponse {

    private UUID id;
    private UUID dividaId;
    private int numero;
    private BigDecimal valor;
    private LocalDate dataVencimento;
    private ParcelaStatus status;
    private UUID lancamentoId;

    public ParcelaResponse() {
    }

    public ParcelaResponse(UUID id, UUID dividaId, int numero, BigDecimal valor, LocalDate dataVencimento, ParcelaStatus status, UUID lancamentoId) {
        this.id = id;
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
}
