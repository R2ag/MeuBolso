package com.meubolso.financeiro.application.dto;

import java.util.UUID;

public class CategoriaResponse {

    private UUID id;
    private String userId;
    private String nome;

    public CategoriaResponse() {
    }

    public CategoriaResponse(UUID id, String userId, String nome) {
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
