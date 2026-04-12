package com.meubolso.api.web;

import com.meubolso.financeiro.application.dto.AtualizarLancamentoRequest;
import com.meubolso.financeiro.application.dto.LancamentoRequest;
import com.meubolso.financeiro.application.dto.LancamentoResponse;
import com.meubolso.financeiro.application.usecase.CriarLancamentoUseCase;
import com.meubolso.financeiro.application.usecase.EditarLancamentoUseCase;
import com.meubolso.financeiro.application.usecase.ExcluirLancamentoUseCase;
import com.meubolso.financeiro.application.usecase.ListarLancamentosUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoController {

    private final CriarLancamentoUseCase criarLancamentoUseCase;
    private final ListarLancamentosUseCase listarLancamentosUseCase;
    private final EditarLancamentoUseCase editarLancamentoUseCase;
    private final ExcluirLancamentoUseCase excluirLancamentoUseCase;

    public LancamentoController(CriarLancamentoUseCase criarLancamentoUseCase,
                                ListarLancamentosUseCase listarLancamentosUseCase,
                                EditarLancamentoUseCase editarLancamentoUseCase,
                                ExcluirLancamentoUseCase excluirLancamentoUseCase) {
        this.criarLancamentoUseCase = criarLancamentoUseCase;
        this.listarLancamentosUseCase = listarLancamentosUseCase;
        this.editarLancamentoUseCase = editarLancamentoUseCase;
        this.excluirLancamentoUseCase = excluirLancamentoUseCase;
    }

    @PostMapping
    public ResponseEntity<LancamentoResponse> criar(@Valid @RequestBody LancamentoRequest request) {
        return ResponseEntity.ok(criarLancamentoUseCase.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<LancamentoResponse>> listar() {
        return ResponseEntity.ok(listarLancamentosUseCase.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LancamentoResponse> atualizar(@PathVariable UUID id,
                                                        @Valid @RequestBody AtualizarLancamentoRequest request) {
        return ResponseEntity.ok(editarLancamentoUseCase.editar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        excluirLancamentoUseCase.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
