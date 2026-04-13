package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.DividaRequest;
import com.meubolso.financeiro.application.dto.DividaResponse;
import com.meubolso.financeiro.application.dto.ParcelaResponse;
import com.meubolso.financeiro.domain.model.Divida;
import com.meubolso.financeiro.domain.model.Parcela;
import com.meubolso.financeiro.domain.model.ParcelaStatus;
import com.meubolso.financeiro.domain.repository.DividaRepository;
import com.meubolso.financeiro.domain.repository.ParcelaRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CriarDividaUseCase {

    private final DividaRepository dividaRepository;
    private final ParcelaRepository parcelaRepository;
    private final UserContext userContext;

    public CriarDividaUseCase(DividaRepository dividaRepository, ParcelaRepository parcelaRepository, UserContext userContext) {
        this.dividaRepository = dividaRepository;
        this.parcelaRepository = parcelaRepository;
        this.userContext = userContext;
    }

    public DividaResponse criar(DividaRequest request) {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }
        if (request == null) {
            throw new IllegalArgumentException("Requisição de dívida é obrigatória");
        }
        LocalDate primeiraParcela = request.getDataPrimeiraParcela();
        if (primeiraParcela == null) {
            throw new IllegalArgumentException("Data da primeira parcela é obrigatória");
        }
        if (request.getNumeroParcelas() <= 0) {
            throw new IllegalArgumentException("Número de parcelas deve ser maior que zero");
        }
        if (request.getValorTotal() == null || request.getValorTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor total da dívida deve ser maior que zero");
        }
        if (request.getTaxaJuros() == null || request.getTaxaJuros().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Taxa de juros não pode ser negativa");
        }
        if (request.getDescricao() == null || request.getDescricao().isBlank()) {
            throw new IllegalArgumentException("Descrição da dívida é obrigatória");
        }
        if (request.getCategoria() == null || request.getCategoria().isBlank()) {
            throw new IllegalArgumentException("Categoria da dívida é obrigatória");
        }

        BigDecimal totalComJuros = request.getValorTotal()
                .multiply(BigDecimal.ONE.add(request.getTaxaJuros().divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP)))
                .setScale(2, RoundingMode.HALF_UP);

        int numeroParcelas = request.getNumeroParcelas();
        BigDecimal parcelaBase = totalComJuros.divide(new BigDecimal(numeroParcelas), 2, RoundingMode.HALF_UP);
        List<Parcela> parcelas = new ArrayList<>();
        BigDecimal somaParcelas = BigDecimal.ZERO;

        UUID dividaId = UUID.randomUUID();
        for (int i = 1; i <= numeroParcelas; i++) {
            LocalDate dataVencimento = primeiraParcela.plusMonths(i - 1);
            BigDecimal valorParcela = parcelaBase;
            if (i == numeroParcelas) {
                valorParcela = totalComJuros.subtract(somaParcelas);
            }
            Parcela parcela = new Parcela(
                    UUID.randomUUID(),
                    userId,
                    dividaId,
                    i,
                    valorParcela,
                    dataVencimento,
                    ParcelaStatus.PENDENTE,
                    null
            );
            parcelas.add(parcela);
            somaParcelas = somaParcelas.add(valorParcela);
        }

        Divida divida = new Divida(
                dividaId,
                userId,
                request.getDescricao(),
                request.getValorTotal(),
                request.getTaxaJuros(),
                numeroParcelas,
                primeiraParcela,
                request.getCategoria(),
                parcelas
        );

        Divida salvo = dividaRepository.save(divida);
        for (Parcela parcela : parcelas) {
            parcelaRepository.save(parcela);
        }

        List<ParcelaResponse> parcelaResponses = new ArrayList<>();
        for (Parcela parcela : salvo.getParcelas()) {
            parcelaResponses.add(new ParcelaResponse(
                    parcela.getId(),
                    salvo.getId(),
                    parcela.getNumero(),
                    parcela.getValor(),
                    parcela.getDataVencimento(),
                    parcela.getStatus(),
                    parcela.getLancamentoId()
            ));
        }

        return new DividaResponse(
                salvo.getId(),
                salvo.getDescricao(),
                salvo.getValorTotal(),
                salvo.getTaxaJuros(),
                salvo.getNumeroParcelas(),
                salvo.getDataPrimeiraParcela(),
                salvo.getCategoria(),
                parcelaResponses
        );
    }
}
