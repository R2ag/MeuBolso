package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.ContaPagarResponse;
import com.meubolso.financeiro.domain.model.ContaPagar;
import com.meubolso.financeiro.domain.repository.ContaPagarRepository;
import com.meubolso.shared.security.UserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarContasPagarUseCaseTest {

    private ContaPagarRepository contaPagarRepository;
    private UserContext userContext;
    private ListarContasPagarUseCase useCase;

    @BeforeEach
    void setUp() {
        contaPagarRepository = mock(ContaPagarRepository.class);
        userContext = mock(UserContext.class);
        useCase = new ListarContasPagarUseCase(contaPagarRepository, userContext);
    }

    @Test
    void shouldListContaPagarForAuthenticatedUser() {
        String userId = "user-5";
        when(userContext.getUserId()).thenReturn(userId);

        ContaPagar conta1 = new ContaPagar(UUID.randomUUID(), userId, "Energia", new BigDecimal("180.00"), LocalDate.of(2026, 5, 15), "Utilidades");
        ContaPagar conta2 = new ContaPagar(UUID.randomUUID(), userId, "Água", new BigDecimal("75.00"), LocalDate.of(2026, 5, 18), "Utilidades");
        when(contaPagarRepository.findByUserId(userId)).thenReturn(List.of(conta1, conta2));

        List<ContaPagarResponse> responses = useCase.listar();

        assertEquals(2, responses.size());
        assertTrue(responses.stream().anyMatch(response -> response.getDescricao().equals("Energia") && response.getValor().compareTo(new BigDecimal("180.00")) == 0));
        assertTrue(responses.stream().anyMatch(response -> response.getDescricao().equals("Água") && response.getValor().compareTo(new BigDecimal("75.00")) == 0));
        verify(contaPagarRepository).findByUserId(userId);
    }
}
