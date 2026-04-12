package com.meubolso.financeiro.infrastructure.persistence;

import com.meubolso.financeiro.domain.model.LancamentoStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "lancamento")
public class LancamentoEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private String conta;

    @Column(nullable = false)
    private String categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LancamentoStatus status;

    protected LancamentoEntity() {
    }

    public LancamentoEntity(UUID id, String userId, String descricao, BigDecimal valor, LocalDate data, String conta, String categoria, LancamentoStatus status) {
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
