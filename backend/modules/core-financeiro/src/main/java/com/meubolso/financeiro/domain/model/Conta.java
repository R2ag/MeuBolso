package com.meubolso.financeiro.domain.model;

import java.util.Objects;
import java.util.UUID;

public class Conta {

    private final UUID id;
    private final String userId;
    private final String nome;

    public Conta(UUID id, String userId, String nome) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conta conta = (Conta) o;
        return id.equals(conta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
