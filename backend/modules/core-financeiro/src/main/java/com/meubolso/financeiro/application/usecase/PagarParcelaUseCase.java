package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.PagarParcelaRequest;
import com.meubolso.financeiro.application.dto.ParcelaResponse;
import com.meubolso.financeiro.domain.model.Divida;
import com.meubolso.financeiro.domain.model.Lancamento;
import com.meubolso.financeiro.domain.model.LancamentoStatus;
import com.meubolso.financeiro.domain.model.Parcela;
import com.meubolso.financeiro.domain.model.ParcelaStatus;
import com.meubolso.financeiro.domain.repository.DividaRepository;
import com.meubolso.financeiro.domain.repository.LancamentoRepository;
import com.meubolso.financeiro.domain.repository.ParcelaRepository;
import com.meubolso.shared.security.UserContext;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PagarParcelaUseCase {

    private final ParcelaRepository parcelaRepository;
    private final DividaRepository dividaRepository;
    private final LancamentoRepository lancamentoRepository;
    private final UserContext userContext;

    public PagarParcelaUseCase(ParcelaRepository parcelaRepository, DividaRepository dividaRepository, LancamentoRepository lancamentoRepository, UserContext userContext) {
        this.parcelaRepository = parcelaRepository;
        this.dividaRepository = dividaRepository;
        this.lancamentoRepository = lancamentoRepository;
        this.userContext = userContext;
    }

    public ParcelaResponse pagar(PagarParcelaRequest request) {
        String userId = userContext.getUserId();
        if (userId == null || userId.isBlank()) {
            throw new IllegalStateException("Usuário não autenticado");
        }
        if (request == null || request.getParcelaId() == null) {
            throw new IllegalArgumentException("Id da parcela é obrigatório");
        }
        if (request.getConta() == null || request.getConta().isBlank()) {
            throw new IllegalArgumentException("Conta do pagamento é obrigatória");
        }
        if (request.getCategoria() == null || request.getCategoria().isBlank()) {
            throw new IllegalArgumentException("Categoria do pagamento é obrigatória");
        }
        if (request.getDataPagamento() == null) {
            throw new IllegalArgumentException("Data do pagamento é obrigatória");
        }

        Parcela parcela = parcelaRepository.findByIdAndUserId(request.getParcelaId(), userId)
                .orElseThrow(() -> new IllegalArgumentException("Parcela não encontrada"));

        if (parcela.isPago()) {
            throw new IllegalStateException("Parcela já foi paga");
        }

        Divida divida = dividaRepository.findByIdAndUserId(parcela.getDividaId(), userId)
                .orElseThrow(() -> new IllegalArgumentException("Dívida da parcela não encontrada"));

        Lancamento lancamento = new Lancamento(
                UUID.randomUUID(),
                userId,
                String.format("Pagamento parcela %d de %s", parcela.getNumero(), divida.getDescricao()),
                parcela.getValor().negate(),
                request.getDataPagamento(),
                request.getConta(),
                request.getCategoria(),
                LancamentoStatus.CONFIRMADO
        );

        Lancamento salvoLancamento = lancamentoRepository.save(lancamento);
        Parcela parcelaPaga = parcela.pagar(salvoLancamento.getId());
        parcelaRepository.save(parcelaPaga);

        return new ParcelaResponse(
                parcelaPaga.getId(),
                parcelaPaga.getDividaId(),
                parcelaPaga.getNumero(),
                parcelaPaga.getValor(),
                parcelaPaga.getDataVencimento(),
                parcelaPaga.getStatus(),
                parcelaPaga.getLancamentoId()
        );
    }
}
