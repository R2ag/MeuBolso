package com.meubolso.financeiro.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class DividaResponse {

    private UUID id;
    private String descricao;
    private BigDecimal valorTotal;
    private BigDecimal taxaJuros;
    private int numeroParcelas;
    private LocalDate dataPrimeiraParcela;
    private String categoria;
    private List<ParcelaResponse> parcelas;

    public DividaResponse() {
    }

    public DividaResponse(UUID id, String descricao, BigDecimal valorTotal, BigDecimal taxaJuros, int numeroParcelas, LocalDate dataPrimeiraParcela, String categoria, List<ParcelaResponse> parcelas) {
        this.id = id;
        this.descricao = descricao;
        this.valorTotal = valorTotal;
        this.taxaJuros = taxaJuros;
        this.numeroParcelas = numeroParcelas;
        this.dataPrimeiraParcela = dataPrimeiraParcela;
        this.categoria = categoria;
        this.parcelas = parcelas;
    }

    public UUID getId() {
        return id;
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

    public List<ParcelaResponse> getParcelas() {
        return parcelas;
    }
}
