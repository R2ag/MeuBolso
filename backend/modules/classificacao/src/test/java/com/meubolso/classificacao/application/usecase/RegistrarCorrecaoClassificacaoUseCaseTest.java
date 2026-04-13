package com.meubolso.classificacao.application.usecase;

import com.meubolso.classificacao.application.dto.RegistrarCorrecaoRequest;
import com.meubolso.classificacao.domain.model.ClassificacaoHistorico;
import com.meubolso.classificacao.domain.repository.ClassificacaoHistoricoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegistrarCorrecaoClassificacaoUseCaseTest {

    private ClassificacaoHistoricoRepository historicoRepository;
    private RegistrarCorrecaoClassificacaoUseCase useCase;

    @BeforeEach
    void setUp() {
        historicoRepository = mock(ClassificacaoHistoricoRepository.class);
        useCase = new RegistrarCorrecaoClassificacaoUseCase(historicoRepository);
    }

    @Test
    void shouldSaveCorrectionHistory() {
        when(historicoRepository.save(any(ClassificacaoHistorico.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RegistrarCorrecaoRequest request = new RegistrarCorrecaoRequest();
        request.setUserId("user-5");
        request.setDescricao("Pizza no delivery");
        request.setContraparte("iFood");
        request.setCategoriaCorrigida("Alimentação");

        ClassificacaoHistorico historico = useCase.registrar(request);

        assertNotNull(historico.getId());
        assertEquals("user-5", historico.getUserId());
        assertEquals("Pizza no delivery", historico.getDescricao());
        assertEquals("Alimentação", historico.getCategoriaCorrigida());
        assertNotNull(historico.getCorrigidoEm());

        verify(historicoRepository).save(any(ClassificacaoHistorico.class));
    }
}
