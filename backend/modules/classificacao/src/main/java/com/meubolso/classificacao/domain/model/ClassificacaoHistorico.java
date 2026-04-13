package com.meubolso.classificacao.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class ClassificacaoHistorico {

    private final UUID id;
    private final String userId;
    private final String descricao;
    private final String contraparte;
    private final String categoriaCorrigida;
    private final LocalDateTime corrigidoEm;

    public ClassificacaoHistorico(UUID id, String userId, String descricao, String contraparte, String categoriaCorrigida, LocalDateTime corrigidoEm) {
        if (id == null) {
            throw new IllegalArgumentException("Id do histórico de classificação não pode ser nulo");
        }
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("UserId do histórico de classificação não pode ser vazio");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição para histórico de classificação é obrigatória");
        }
        if (categoriaCorrigida == null || categoriaCorrigida.isBlank()) {
            throw new IllegalArgumentException("Categoria corrigida é obrigatória no histórico de classificação");
        }
        if (corrigidoEm == null) {
            throw new IllegalArgumentException("Data de correção é obrigatória");
        }
        this.id = id;
        this.userId = userId;
        this.descricao = descricao;
        this.contraparte = contraparte;
        this.categoriaCorrigida = categoriaCorrigida;
        this.corrigidoEm = corrigidoEm;
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

    public String getContraparte() {
        return contraparte;
    }

    public String getCategoriaCorrigida() {
        return categoriaCorrigida;
    }

    public LocalDateTime getCorrigidoEm() {
        return corrigidoEm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassificacaoHistorico that = (ClassificacaoHistorico) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
