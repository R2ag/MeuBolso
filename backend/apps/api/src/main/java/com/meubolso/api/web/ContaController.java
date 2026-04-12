package com.meubolso.api.web;

import com.meubolso.financeiro.application.dto.ContaRequest;
import com.meubolso.financeiro.application.dto.ContaResponse;
import com.meubolso.financeiro.application.usecase.CriarContaUseCase;
import com.meubolso.financeiro.application.usecase.ListarContasUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    private final CriarContaUseCase criarContaUseCase;
    private final ListarContasUseCase listarContasUseCase;

    public ContaController(CriarContaUseCase criarContaUseCase, ListarContasUseCase listarContasUseCase) {
        this.criarContaUseCase = criarContaUseCase;
        this.listarContasUseCase = listarContasUseCase;
    }

    @PostMapping
    public ResponseEntity<ContaResponse> criar(@Valid @RequestBody ContaRequest request) {
        return ResponseEntity.ok(criarContaUseCase.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<ContaResponse>> listar() {
        return ResponseEntity.ok(listarContasUseCase.listar());
    }
}
