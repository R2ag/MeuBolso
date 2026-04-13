package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.AcompanharOrcamentoRequest;
import com.meubolso.financeiro.application.dto.OrcamentoResumoResponse;
import com.meubolso.financeiro.domain.model.Lancamento;
import com.meubolso.financeiro.domain.model.Orcamento;
import com.meubolso.financeiro.domain.repository.LancamentoRepository;
import com.meubolso.financeiro.domain.repository.OrcamentoRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AcompanharOrcamentoUseCase {

    private final OrcamentoRepository orcamentoRepository;
    private final LancamentoRepository lancamentoRepository;
    private final UserContext userContext;

    public AcompanharOrcamentoUseCase(OrcamentoRepository orcamentoRepository, LancamentoRepository lancamentoRepository, UserContext userContext) {
        this.orcamentoRepository = orcamentoRepository;
        this.lancamentoRepository = lancamentoRepository;
        this.userContext = userContext;
    }

    public List<OrcamentoResumoResponse> acompanhar(AcompanharOrcamentoRequest request) {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }

        int ano = request.getAno();
        int mes = request.getMes();

        Map<String, BigDecimal> realizadoPorCategoria = lancamentoRepository.findByUserId(userId).stream()
                .filter(lancamento -> lancamento.getData().getYear() == ano && lancamento.getData().getMonthValue() == mes)
                .collect(Collectors.groupingBy(Lancamento::getCategoria,
                        Collectors.mapping(Lancamento::getValor, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

        return orcamentoRepository.findByUserId(userId).stream()
                .filter(orcamento -> orcamento.getAno() == ano && orcamento.getMes() == mes)
                .map(orcamento -> {
                    BigDecimal realizado = realizadoPorCategoria.getOrDefault(orcamento.getCategoria(), BigDecimal.ZERO);
                    if (realizado.compareTo(BigDecimal.ZERO) < 0) {
                        realizado = realizado.abs();
                    }
                    BigDecimal saldoRestante = orcamento.getValor().subtract(realizado);
                    return new OrcamentoResumoResponse(
                            orcamento.getId(),
                            orcamento.getCategoria(),
                            orcamento.getAno(),
                            orcamento.getMes(),
                            orcamento.getValor(),
                            realizado,
                            saldoRestante);
                })
                .collect(Collectors.toList());
    }
}
