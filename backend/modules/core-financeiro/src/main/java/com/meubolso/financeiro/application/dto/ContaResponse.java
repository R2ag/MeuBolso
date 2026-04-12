package com.meubolso.financeiro.application.dto;

import java.util.UUID;

public class ContaResponse {

    private UUID id;
    private String userId;
    private String nome;

    public ContaResponse() {
    }

    public ContaResponse(UUID id, String userId, String nome) {
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
