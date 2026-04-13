package com.meubolso.classificacao.domain.service;

import com.meubolso.classificacao.domain.model.ClassificacaoHistorico;
import com.meubolso.classificacao.domain.model.ClassificacaoSugestao;
import com.meubolso.classificacao.domain.repository.ClassificacaoHistoricoRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassificacaoService {

    private final ClassificacaoHistoricoRepository historicoRepository;

    private static final Map<String, String> REGRAS_EXPLICITAS = Map.of(
            "supermercado", "Alimentação",
            "mercado", "Alimentação",
            "uber", "Transporte",
            "ifood", "Alimentação",
            "farmácia", "Saúde",
            "internet", "Casa",
            "aluguel", "Casa",
            "luz", "Casa",
            "água", "Casa"
    );

    public ClassificacaoService(ClassificacaoHistoricoRepository historicoRepository) {
        this.historicoRepository = historicoRepository;
    }

    public ClassificacaoSugestao sugerir(String userId, String descricao, String contraparte) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("Usuário não informado");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição é obrigatória para sugestão");
        }

        String texto = descricao.toLowerCase(Locale.ROOT).trim();
        String complemento = contraparte == null ? "" : contraparte.toLowerCase(Locale.ROOT).trim();

        Optional<ClassificacaoSugestao> regra = aplicarRegraExplicita(texto, complemento);
        if (regra.isPresent()) {
            return regra.get();
        }

        List<ClassificacaoHistorico> historicos = historicoRepository.findByUserId(userId);

        Optional<ClassificacaoSugestao> historicoExato = aplicarHistoricoExato(texto, complemento, historicos);
        if (historicoExato.isPresent()) {
            return historicoExato.get();
        }

        Optional<ClassificacaoSugestao> historicoSimilar = aplicarSimilaridade(texto, complemento, historicos);
        if (historicoSimilar.isPresent()) {
            return historicoSimilar.get();
        }

        return new ClassificacaoSugestao("Sem categoria", contraparte, 0.50, "fallback");
    }

    public ClassificacaoHistorico registrarCorrecao(ClassificacaoHistorico historico) {
        return historicoRepository.save(historico);
    }

    private Optional<ClassificacaoSugestao> aplicarRegraExplicita(String texto, String complemento) {
        for (Map.Entry<String, String> regra : REGRAS_EXPLICITAS.entrySet()) {
            if (texto.contains(regra.getKey()) || complemento.contains(regra.getKey())) {
                return Optional.of(new ClassificacaoSugestao(regra.getValue(), complemento, 0.95, "regra"));
            }
        }
        return Optional.empty();
    }

    private Optional<ClassificacaoSugestao> aplicarHistoricoExato(String texto, String complemento, List<ClassificacaoHistorico> historicos) {
        return historicos.stream()
                .filter(h -> normalizar(h.getDescricao()).equals(texto) && normalizar(h.getContraparte()).equals(complemento))
                .max(Comparator.comparing(ClassificacaoHistorico::getCorrigidoEm))
                .map(h -> new ClassificacaoSugestao(h.getCategoriaCorrigida(), complemento, 0.90, "histórico-exato"));
    }

    private Optional<ClassificacaoSugestao> aplicarSimilaridade(String texto, String complemento, List<ClassificacaoHistorico> historicos) {
        return historicos.stream()
                .map(h -> Map.entry(h, calcularSimilaridade(texto, normalizar(h.getDescricao())) + calcularSimilaridade(complemento, normalizar(h.getContraparte()))))
                .filter(entry -> entry.getValue() >= 1.0)
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .map(entry -> new ClassificacaoSugestao(entry.getKey().getCategoriaCorrigida(), complemento, 0.75, "similaridade"));
    }

    private double calcularSimilaridade(String texto1, String texto2) {
        if (texto1.isBlank() || texto2.isBlank()) {
            return 0.0;
        }
        List<String> tokens1 = List.of(texto1.split("\\s+"));
        List<String> tokens2 = List.of(texto2.split("\\s+"));
        long interseccao = tokens1.stream().filter(tokens2::contains).distinct().count();
        long total = Math.max(tokens1.size(), tokens2.size());
        return total == 0 ? 0.0 : (double) interseccao / total;
    }

    private String normalizar(String texto) {
        return texto == null ? "" : texto.toLowerCase(Locale.ROOT).trim();
    }
}
