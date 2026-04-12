package com.meubolso.api.web;

import com.meubolso.financeiro.application.dto.CategoriaRequest;
import com.meubolso.financeiro.application.dto.CategoriaResponse;
import com.meubolso.financeiro.application.usecase.CriarCategoriaUseCase;
import com.meubolso.financeiro.application.usecase.ListarCategoriasUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CriarCategoriaUseCase criarCategoriaUseCase;
    private final ListarCategoriasUseCase listarCategoriasUseCase;

    public CategoriaController(CriarCategoriaUseCase criarCategoriaUseCase, ListarCategoriasUseCase listarCategoriasUseCase) {
        this.criarCategoriaUseCase = criarCategoriaUseCase;
        this.listarCategoriasUseCase = listarCategoriasUseCase;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> criar(@Valid @RequestBody CategoriaRequest request) {
        return ResponseEntity.ok(criarCategoriaUseCase.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listar() {
        return ResponseEntity.ok(listarCategoriasUseCase.listar());
    }
}
