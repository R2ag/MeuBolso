package com.meubolso.importacao.application.parser;

import com.meubolso.importacao.domain.model.TransacaoStaging;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class ImportacaoParser {

    public List<TransacaoStaging> parse(byte[] content, UUID importacaoId, String userId, String arquivoNome) {
        if (content == null || content.length == 0) {
            throw new IllegalArgumentException("Conteúdo do arquivo de importação não pode ser vazio");
        }
        if (importacaoId == null) {
            throw new IllegalArgumentException("Id da importação não pode ser nulo");
        }
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("UserId não pode ser vazio");
        }
        if (arquivoNome == null || arquivoNome.isBlank()) {
            throw new IllegalArgumentException("Nome do arquivo é obrigatório");
        }

        String contentString = new String(content, StandardCharsets.UTF_8);
        if (isOfx(arquivoNome, contentString)) {
            return parseOfx(contentString, importacaoId, userId);
        }
        return parseCsv(contentString, importacaoId, userId);
    }

    private List<TransacaoStaging> parseCsv(String content, UUID importacaoId, String userId) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8))) {
            String line;
            List<TransacaoStaging> parsed = new ArrayList<>();
            Set<String> uniqueKeys = new HashSet<>();

            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }
                if (isHeaderLine(trimmed)) {
                    continue;
                }

                String[] columns = trimmed.split("\\s*[;,]\\s*");
                if (columns.length < 5) {
                    throw new IllegalArgumentException("Linha de importação inválida: " + trimmed);
                }

                String descricao = columns[0].trim();
                BigDecimal valor = new BigDecimal(columns[1].trim());
                LocalDate data = LocalDate.parse(columns[2].trim());
                String conta = columns[3].trim();
                String categoria = columns[4].trim();

                String uniqueKey = String.join("|", descricao, valor.toPlainString(), data.toString(), conta, categoria);
                if (!uniqueKeys.add(uniqueKey)) {
                    continue;
                }

                parsed.add(new TransacaoStaging(
                        UUID.randomUUID(),
                        importacaoId,
                        userId,
                        descricao,
                        valor,
                        data,
                        conta,
                        categoria
                ));
            }

            return parsed;
        } catch (Exception e) {
            throw new IllegalArgumentException("Falha ao processar arquivo de importação", e);
        }
    }

    private List<TransacaoStaging> parseOfx(String content, UUID importacaoId, String userId) {
        List<TransacaoStaging> parsed = new ArrayList<>();
        Set<String> uniqueKeys = new HashSet<>();

        String normalized = content.replace("\r\n", "\n").replace("\r", "\n");
        String[] blocks = normalized.split("(?i)<stmttrn>");

        for (int index = 1; index < blocks.length; index++) {
            String block = blocks[index];
            String descricao = findTagValue(block, "NAME");
            if (descricao.isBlank()) {
                descricao = findTagValue(block, "MEMO");
            }
            if (descricao.isBlank()) {
                throw new IllegalArgumentException("Transação OFX sem descrição");
            }

            String valorText = findTagValue(block, "TRNAMT");
            if (valorText.isBlank()) {
                throw new IllegalArgumentException("Transação OFX sem valor");
            }
            BigDecimal valor = new BigDecimal(valorText.trim());

            String dataText = findTagValue(block, "DTPOSTED");
            if (dataText.isBlank()) {
                throw new IllegalArgumentException("Transação OFX sem data");
            }
            LocalDate data = parseOfxDate(dataText.trim());

            String conta = "Conta OFX";
            String categoria = "Sem categoria";

            String uniqueKey = String.join("|", descricao, valor.toPlainString(), data.toString(), conta, categoria);
            if (!uniqueKeys.add(uniqueKey)) {
                continue;
            }

            parsed.add(new TransacaoStaging(
                    UUID.randomUUID(),
                    importacaoId,
                    userId,
                    descricao,
                    valor,
                    data,
                    conta,
                    categoria
            ));
        }

        return parsed;
    }

    private LocalDate parseOfxDate(String dateText) {
        String normalized = dateText.replaceAll("\\D", "");
        if (normalized.length() < 8) {
            throw new IllegalArgumentException("Data OFX inválida: " + dateText);
        }
        String trimmed = normalized.substring(0, 8);
        return LocalDate.parse(trimmed, DateTimeFormatter.BASIC_ISO_DATE);
    }

    private String findTagValue(String content, String tag) {
        String lower = content.toLowerCase();
        String open = "<" + tag.toLowerCase() + ">";
        int start = lower.indexOf(open);
        if (start < 0) {
            return "";
        }
        int valueStart = start + open.length();
        int valueEnd = content.indexOf("<", valueStart);
        if (valueEnd < 0) {
            valueEnd = content.length();
        }
        return content.substring(valueStart, valueEnd).trim();
    }

    private boolean isHeaderLine(String line) {
        String lower = line.toLowerCase();
        return lower.contains("descricao")
                && lower.contains("valor")
                && lower.contains("data")
                && lower.contains("conta")
                && lower.contains("categoria");
    }

    private boolean isOfx(String arquivoNome, String content) {
        String lowerName = arquivoNome.toLowerCase();
        return lowerName.endsWith(".ofx") || content.toLowerCase().contains("<ofx>") || content.toLowerCase().contains("ofxheader:");
    }
}
