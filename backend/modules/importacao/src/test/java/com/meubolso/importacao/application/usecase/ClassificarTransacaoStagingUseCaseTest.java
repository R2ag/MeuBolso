package com.meubolso.importacao.application.usecase;

import com.meubolso.classificacao.application.usecase.RegistrarCorrecaoClassificacaoUseCase;
import com.meubolso.importacao.application.dto.AtualizarTransacaoStagingRequest;
import com.meubolso.importacao.application.dto.TransacaoStagingResponse;
import com.meubolso.importacao.domain.model.TransacaoStaging;
import com.meubolso.importacao.domain.model.TransacaoStagingStatus;
import com.meubolso.importacao.domain.repository.TransacaoStagingRepository;
import com.meubolso.shared.security.UserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClassificarTransacaoStagingUseCaseTest {

    private TransacaoStagingRepository stagingRepository;
    private UserContext userContext;
    private RegistrarCorrecaoClassificacaoUseCase registrarCorrecaoClassificacaoUseCase;
    private ClassificarTransacaoStagingUseCase useCase;

    @BeforeEach
    void setUp() {
        stagingRepository = mock(TransacaoStagingRepository.class);
        userContext = mock(UserContext.class);
        registrarCorrecaoClassificacaoUseCase = mock(RegistrarCorrecaoClassificacaoUseCase.class);
        useCase = new ClassificarTransacaoStagingUseCase(stagingRepository, userContext, registrarCorrecaoClassificacaoUseCase);
    }

    @Test
    void shouldClassifyStagingTransaction() {
        UUID importacaoId = UUID.randomUUID();
        UUID transacaoId = UUID.randomUUID();
        String userId = "user-1";
        TransacaoStaging staging = new TransacaoStaging(transacaoId, importacaoId, userId, "Mercado", new BigDecimal("100.00"), LocalDate.of(2026, 4, 12), "Conta corrente", "Alimentacao");
        AtualizarTransacaoStagingRequest request = new AtualizarTransacaoStagingRequest();
        request.setConta("Cartao de credito");
        request.setCategoria("Compras");

        when(userContext.getUserId()).thenReturn(userId);
        when(stagingRepository.findById(transacaoId)).thenReturn(Optional.of(staging));
        when(stagingRepository.save(any(TransacaoStaging.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TransacaoStagingResponse response = useCase.classificar(importacaoId, transacaoId, request);

        assertNotNull(response);
        assertEquals(transacaoId, response.getId());
        assertEquals("Cartao de credito", response.getConta());
        assertEquals("Compras", response.getCategoria());
        assertEquals(TransacaoStagingStatus.CLASSIFICADA.name(), response.getStatus());
        verify(stagingRepository).save(any(TransacaoStaging.class));
        verify(registrarCorrecaoClassificacaoUseCase).registrar(any());
    }
}
