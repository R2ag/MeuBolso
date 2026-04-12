package com.meubolso.api.web;

import com.meubolso.financeiro.application.dto.CategoriaRequest;
import com.meubolso.financeiro.application.dto.CategoriaResponse;
import com.meubolso.financeiro.application.usecase.CriarCategoriaUseCase;
import com.meubolso.financeiro.application.usecase.ExcluirCategoriaUseCase;
import com.meubolso.financeiro.application.usecase.ListarCategoriasUseCase;
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
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CriarCategoriaUseCase criarCategoriaUseCase;
    private final ListarCategoriasUseCase listarCategoriasUseCase;
    private final ExcluirCategoriaUseCase excluirCategoriaUseCase;

    public CategoriaController(CriarCategoriaUseCase criarCategoriaUseCase,
                               ListarCategoriasUseCase listarCategoriasUseCase,
                               ExcluirCategoriaUseCase excluirCategoriaUseCase) {
        this.criarCategoriaUseCase = criarCategoriaUseCase;
        this.listarCategoriasUseCase = listarCategoriasUseCase;
        this.excluirCategoriaUseCase = excluirCategoriaUseCase;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> criar(@Valid @RequestBody CategoriaRequest request) {
        return ResponseEntity.ok(criarCategoriaUseCase.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listar() {
        return ResponseEntity.ok(listarCategoriasUseCase.listar());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        excluirCategoriaUseCase.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
