package com.meubolso.importacao.application.dto;

import java.time.Instant;
import java.util.UUID;

public class ImportacaoResponse {

    private UUID id;
    private String arquivoNome;
    private Instant criadoEm;
    private String status;

    public ImportacaoResponse() {
    }

    public ImportacaoResponse(UUID id, String arquivoNome, Instant criadoEm, String status) {
        this.id = id;
        this.arquivoNome = arquivoNome;
        this.criadoEm = criadoEm;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public String getArquivoNome() {
        return arquivoNome;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public String getStatus() {
        return status;
    }
}
