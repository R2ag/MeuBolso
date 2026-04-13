package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.ProjecaoSaldoResponse;
import com.meubolso.financeiro.domain.model.ContaPagar;
import com.meubolso.financeiro.domain.model.ContaReceber;
import com.meubolso.financeiro.domain.model.Divida;
import com.meubolso.financeiro.domain.model.Parcela;
import com.meubolso.financeiro.domain.model.ParcelaStatus;
import com.meubolso.financeiro.domain.model.Lancamento;
import com.meubolso.financeiro.domain.repository.ContaPagarRepository;
import com.meubolso.financeiro.domain.repository.ContaReceberRepository;
import com.meubolso.financeiro.domain.repository.DividaRepository;
import com.meubolso.financeiro.domain.repository.LancamentoRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalcularProjecaoSaldoUseCase {

    private final LancamentoRepository lancamentoRepository;
    private final ContaReceberRepository contaReceberRepository;
    private final ContaPagarRepository contaPagarRepository;
    private final DividaRepository dividaRepository;
    private final UserContext userContext;

    public CalcularProjecaoSaldoUseCase(LancamentoRepository lancamentoRepository, ContaReceberRepository contaReceberRepository, ContaPagarRepository contaPagarRepository, DividaRepository dividaRepository, UserContext userContext) {
        this.lancamentoRepository = lancamentoRepository;
        this.contaReceberRepository = contaReceberRepository;
        this.contaPagarRepository = contaPagarRepository;
        this.dividaRepository = dividaRepository;
        this.userContext = userContext;
    }

    public ProjecaoSaldoResponse calcular() {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }

        BigDecimal saldoAtual = lancamentoRepository.findByUserId(userId).stream()
                .map(Lancamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal entradasFuturas = contaReceberRepository.findByUserId(userId).stream()
                .map(ContaReceber::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saidasFuturas = contaPagarRepository.findByUserId(userId).stream()
                .map(ContaPagar::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal parcelaPendentes = dividaRepository.findByUserId(userId).stream()
                .flatMap(divida -> divida.getParcelas().stream())
                .filter(parcela -> parcela.getStatus() == ParcelaStatus.PENDENTE)
                .map(Parcela::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSaidasFuturas = saidasFuturas.add(parcelaPendentes);
        BigDecimal saldoProjetado = saldoAtual.add(entradasFuturas).subtract(totalSaidasFuturas);

        return new ProjecaoSaldoResponse(saldoAtual, entradasFuturas, totalSaidasFuturas, saldoProjetado);
    }
}
