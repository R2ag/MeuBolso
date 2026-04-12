package com.meubolso.financeiro.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "conta")
public class ContaEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String nome;

    protected ContaEntity() {
    }

    public ContaEntity(UUID id, String userId, String nome) {
        this.id = id;
        this.userId = userId;
        this.nome = nome;
    }

    public UUID getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getNome() {
        return nome;
    }
}
