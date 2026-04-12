package com.meubolso.importacao.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Importacao {

    private final UUID id;
    private final String userId;
    private final String arquivoNome;
    private final Instant criadoEm;
    private final ImportacaoStatus status;

    public Importacao(UUID id, String userId, String arquivoNome, Instant criadoEm, ImportacaoStatus status) {
        if (id == null) {
            throw new IllegalArgumentException("Id de importação não pode ser nulo");
        }
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("UserId de importação não pode ser vazio");
        }
        if (arquivoNome == null || arquivoNome.isBlank()) {
            throw new IllegalArgumentException("Nome de arquivo da importação é obrigatório");
        }
        if (criadoEm == null) {
            throw new IllegalArgumentException("Data de criação da importação é obrigatória");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status da importação é obrigatório");
        }

        this.id = id;
        this.userId = userId;
        this.arquivoNome = arquivoNome;
        this.criadoEm = criadoEm;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getArquivoNome() {
        return arquivoNome;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public ImportacaoStatus getStatus() {
        return status;
    }

    public Importacao revisar() {
        return new Importacao(id, userId, arquivoNome, criadoEm, ImportacaoStatus.REVISAO);
    }

    public Importacao confirmar() {
        return new Importacao(id, userId, arquivoNome, criadoEm, ImportacaoStatus.CONFIRMADA);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Importacao that = (Importacao) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
