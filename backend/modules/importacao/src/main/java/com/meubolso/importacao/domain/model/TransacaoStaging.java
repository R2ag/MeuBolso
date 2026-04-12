package com.meubolso.importacao.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class TransacaoStaging {

    private final UUID id;
    private final UUID importacaoId;
    private final String userId;
    private final String descricao;
    private final BigDecimal valor;
    private final LocalDate data;
    private final String conta;
    private final String categoria;
    private final TransacaoStagingStatus status;

    public TransacaoStaging(UUID id, UUID importacaoId, String userId, String descricao, BigDecimal valor, LocalDate data, String conta, String categoria) {
        this(id, importacaoId, userId, descricao, valor, data, conta, categoria, TransacaoStagingStatus.PENDENTE);
    }

    public TransacaoStaging(UUID id, UUID importacaoId, String userId, String descricao, BigDecimal valor, LocalDate data, String conta, String categoria, TransacaoStagingStatus status) {
        if (id == null) {
            throw new IllegalArgumentException("Id de transação em staging não pode ser nulo");
        }
        if (importacaoId == null) {
            throw new IllegalArgumentException("Id da importação não pode ser nulo");
        }
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("UserId da transação não pode ser vazio");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição da transação é obrigatória");
        }
        if (valor == null) {
            throw new IllegalArgumentException("Valor da transação é obrigatório");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data da transação é obrigatória");
        }
        if (conta == null || conta.isBlank()) {
            throw new IllegalArgumentException("Conta da transação é obrigatória");
        }
        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("Categoria da transação é obrigatória");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status da transação é obrigatório");
        }

        this.id = id;
        this.importacaoId = importacaoId;
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

    public UUID getImportacaoId() {
        return importacaoId;
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

    public TransacaoStagingStatus getStatus() {
        return status;
    }

    public TransacaoStaging classificar(String descricao, String conta, String categoria) {
        return new TransacaoStaging(
                id,
                importacaoId,
                userId,
                descricao == null || descricao.isBlank() ? this.descricao : descricao,
                valor,
                data,
                conta == null || conta.isBlank() ? this.conta : conta,
                categoria == null || categoria.isBlank() ? this.categoria : categoria,
                TransacaoStagingStatus.CLASSIFICADA
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransacaoStaging that = (TransacaoStaging) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
