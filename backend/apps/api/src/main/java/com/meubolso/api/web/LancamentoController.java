package com.meubolso.api.web;

import com.meubolso.financeiro.application.dto.LancamentoRequest;
import com.meubolso.financeiro.application.dto.LancamentoResponse;
import com.meubolso.financeiro.application.usecase.CriarLancamentoUseCase;
import com.meubolso.financeiro.application.usecase.ListarLancamentosUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoController {

    private final CriarLancamentoUseCase criarLancamentoUseCase;
    private final ListarLancamentosUseCase listarLancamentosUseCase;

    public LancamentoController(CriarLancamentoUseCase criarLancamentoUseCase, ListarLancamentosUseCase listarLancamentosUseCase) {
        this.criarLancamentoUseCase = criarLancamentoUseCase;
        this.listarLancamentosUseCase = listarLancamentosUseCase;
    }

    @PostMapping
    public ResponseEntity<LancamentoResponse> criar(@Valid @RequestBody LancamentoRequest request) {
        LancamentoResponse response = criarLancamentoUseCase.criar(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<LancamentoResponse>> listar() {
        return ResponseEntity.ok(listarLancamentosUseCase.listar());
    }
}
