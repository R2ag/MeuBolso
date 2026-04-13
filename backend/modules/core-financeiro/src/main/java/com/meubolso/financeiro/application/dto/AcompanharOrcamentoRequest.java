package com.meubolso.financeiro.application.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class AcompanharOrcamentoRequest {

    @Min(1900)
    private int ano;

    @Min(1)
    @Max(12)
    private int mes;

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }
}
