package com.meubolso.api.web;

import com.meubolso.financeiro.application.dto.ContaRequest;
import com.meubolso.financeiro.application.dto.ContaResponse;
import com.meubolso.financeiro.application.usecase.CriarContaUseCase;
import com.meubolso.financeiro.application.usecase.ExcluirContaUseCase;
import com.meubolso.financeiro.application.usecase.ListarContasUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    private final CriarContaUseCase criarContaUseCase;
    private final ListarContasUseCase listarContasUseCase;
    private final ExcluirContaUseCase excluirContaUseCase;

    public ContaController(CriarContaUseCase criarContaUseCase,
                           ListarContasUseCase listarContasUseCase,
                           ExcluirContaUseCase excluirContaUseCase) {
        this.criarContaUseCase = criarContaUseCase;
        this.listarContasUseCase = listarContasUseCase;
        this.excluirContaUseCase = excluirContaUseCase;
    }

    @PostMapping
    public ResponseEntity<ContaResponse> criar(@Valid @RequestBody ContaRequest request) {
        return ResponseEntity.ok(criarContaUseCase.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<ContaResponse>> listar() {
        return ResponseEntity.ok(listarContasUseCase.listar());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        excluirContaUseCase.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
