package com.meubolso.importacao.application.parser;

import com.meubolso.importacao.domain.model.TransacaoStaging;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ImportacaoParserTest {

    private final ImportacaoParser parser = new ImportacaoParser();

    @Test
    void shouldIgnoreDuplicateTransactionsWhileParsingCsv() {
        String csv = "descricao;valor;data;conta;categoria\n"
                + "Pagamento mercado;100.00;2026-04-12;Conta corrente;Alimentacao\n"
                + "Pagamento mercado;100.00;2026-04-12;Conta corrente;Alimentacao\n"
                + "Salario;2000.00;2026-04-10;Conta poupança;Receita";

        List<TransacaoStaging> transacoes = parser.parse(csv.getBytes(), UUID.randomUUID(), "user-1", "transacoes.csv");

        assertEquals(2, transacoes.size());
        assertTrue(transacoes.stream().anyMatch(t -> t.getDescricao().equals("Pagamento mercado")));
        assertTrue(transacoes.stream().anyMatch(t -> t.getDescricao().equals("Salario")));
    }

    @Test
    void shouldParseOfxTransactions() {
        String ofx = "OFXHEADER:100\n"
                + "DATA:OFXSGML\n"
                + "VERSION:102\n"
                + "SECURITY:NONE\n"
                + "ENCODING:UTF-8\n"
                + "CHARSET:1252\n"
                + "COMPRESSION:NONE\n"
                + "OLDFILEUID:NONE\n"
                + "NEWFILEUID:NONE\n"
                + "<OFX>\n"
                + "<BANKMSGSRSV1>\n"
                + "<STMTTRNRS>\n"
                + "<STMTRS>\n"
                + "<BANKTRANLIST>\n"
                + "<STMTTRN>\n"
                + "<TRNTYPE>DEBIT\n"
                + "<DTPOSTED>20260412\n"
                + "<TRNAMT>-150.00\n"
                + "<FITID>1\n"
                + "<NAME>Pagamento mercado\n"
                + "</STMTTRN>\n"
                + "<STMTTRN>\n"
                + "<TRNTYPE>CREDIT\n"
                + "<DTPOSTED>20260410\n"
                + "<TRNAMT>2000.00\n"
                + "<FITID>2\n"
                + "<NAME>Salario\n"
                + "</STMTTRN>\n"
                + "</BANKTRANLIST>\n"
                + "</STMTRS>\n"
                + "</STMTTRNRS>\n"
                + "</BANKMSGSRSV1>\n"
                + "</OFX>";

        List<TransacaoStaging> transacoes = parser.parse(ofx.getBytes(), UUID.randomUUID(), "user-1", "transacoes.ofx");

        assertEquals(2, transacoes.size());
        assertTrue(transacoes.stream().anyMatch(t -> t.getDescricao().equals("Pagamento mercado") && t.getValor().compareTo(new java.math.BigDecimal("-150.00")) == 0));
        assertTrue(transacoes.stream().anyMatch(t -> t.getDescricao().equals("Salario") && t.getValor().compareTo(new java.math.BigDecimal("2000.00")) == 0));
        assertTrue(transacoes.stream().allMatch(t -> t.getCategoria().equals("Sem categoria")));
    }
}
