-- V1: criando as tabelas base do módulo core-financeiro

CREATE TABLE IF NOT EXISTS conta (
    id UUID PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS categoria (
    id UUID PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS lancamento (
    id UUID PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    descricao VARCHAR(1000) NOT NULL,
    valor NUMERIC(19, 2) NOT NULL,
    data DATE NOT NULL,
    conta VARCHAR(255) NOT NULL,
    categoria VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL
);
