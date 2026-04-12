package com.meubolso.importacao.web;

import com.meubolso.importacao.application.dto.AtualizarTransacaoStagingRequest;
import com.meubolso.importacao.application.dto.ImportacaoResponse;
import com.meubolso.importacao.application.dto.TransacaoStagingResponse;
import com.meubolso.importacao.application.usecase.ClassificarTransacaoStagingUseCase;
import com.meubolso.importacao.application.usecase.ConfirmarImportacaoUseCase;
import com.meubolso.importacao.application.usecase.ImportarTransacoesUseCase;
import com.meubolso.importacao.application.usecase.ListarImportacoesUseCase;
import com.meubolso.importacao.application.usecase.ListarTransacoesStagingUseCase;
import com.meubolso.importacao.application.usecase.RevisarImportacaoUseCase;
import com.meubolso.shared.security.UserContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/importacoes")
public class ImportacaoController {

    private final ImportarTransacoesUseCase importarTransacoesUseCase;
    private final ListarImportacoesUseCase listarImportacoesUseCase;
    private final ListarTransacoesStagingUseCase listarTransacoesStagingUseCase;
    private final ClassificarTransacaoStagingUseCase classificarTransacaoStagingUseCase;
    private final RevisarImportacaoUseCase revisarImportacaoUseCase;
    private final ConfirmarImportacaoUseCase confirmarImportacaoUseCase;
    private final UserContext userContext;

    public ImportacaoController(ImportarTransacoesUseCase importarTransacoesUseCase,
                                ListarImportacoesUseCase listarImportacoesUseCase,
                                ListarTransacoesStagingUseCase listarTransacoesStagingUseCase,
                                ClassificarTransacaoStagingUseCase classificarTransacaoStagingUseCase,
                                RevisarImportacaoUseCase revisarImportacaoUseCase,
                                ConfirmarImportacaoUseCase confirmarImportacaoUseCase,
                                UserContext userContext) {
        this.importarTransacoesUseCase = importarTransacoesUseCase;
        this.listarImportacoesUseCase = listarImportacoesUseCase;
        this.listarTransacoesStagingUseCase = listarTransacoesStagingUseCase;
        this.classificarTransacaoStagingUseCase = classificarTransacaoStagingUseCase;
        this.revisarImportacaoUseCase = revisarImportacaoUseCase;
        this.confirmarImportacaoUseCase = confirmarImportacaoUseCase;
        this.userContext = userContext;
    }

    @PostMapping("/upload")
    public ResponseEntity<ImportacaoResponse> upload(@RequestParam("file") MultipartFile file) throws IOException {
        String userId = userContext.getUserId();
        byte[] content = file.getBytes();
        var importacao = importarTransacoesUseCase.importar(content, file.getOriginalFilename(), userId);
        return ResponseEntity.ok(new ImportacaoResponse(importacao.getId(), importacao.getArquivoNome(), importacao.getCriadoEm(), importacao.getStatus().name()));
    }

    @GetMapping
    public ResponseEntity<List<ImportacaoResponse>> listar() {
        return ResponseEntity.ok(listarImportacoesUseCase.listar());
    }

    @GetMapping("/{id}/staging")
    public ResponseEntity<List<TransacaoStagingResponse>> preview(@PathVariable UUID id) {
        return ResponseEntity.ok(listarTransacoesStagingUseCase.listar(id, userContext.getUserId()));
    }

    @PatchMapping("/{importacaoId}/transacoes/{transacaoId}")
    public ResponseEntity<TransacaoStagingResponse> classificar(@PathVariable UUID importacaoId,
                                                                 @PathVariable UUID transacaoId,
                                                                 @RequestBody AtualizarTransacaoStagingRequest request) {
        return ResponseEntity.ok(classificarTransacaoStagingUseCase.classificar(importacaoId, transacaoId, request));
    }

    @PostMapping("/{id}/revisar")
    public ResponseEntity<ImportacaoResponse> revisar(@PathVariable UUID id) {
        return ResponseEntity.ok(revisarImportacaoUseCase.revisar(id));
    }

    @PostMapping("/{id}/confirmar")
    public ResponseEntity<ImportacaoResponse> confirmar(@PathVariable UUID id) {
        return ResponseEntity.ok(confirmarImportacaoUseCase.confirmar(id, userContext.getUserId()));
    }
}
