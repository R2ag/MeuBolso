package com.meubolso.financeiro.application.usecase;

import com.meubolso.financeiro.application.dto.ContaReceberResponse;
import com.meubolso.financeiro.domain.model.ContaReceber;
import com.meubolso.financeiro.domain.repository.ContaReceberRepository;
import com.meubolso.shared.security.UserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarContasReceberUseCaseTest {

    private ContaReceberRepository contaReceberRepository;
    private UserContext userContext;
    private ListarContasReceberUseCase useCase;

    @BeforeEach
    void setUp() {
        contaReceberRepository = mock(ContaReceberRepository.class);
        userContext = mock(UserContext.class);
        useCase = new ListarContasReceberUseCase(contaReceberRepository, userContext);
    }

    @Test
    void shouldListContaReceberForAuthenticatedUser() {
        String userId = "user-6";
        when(userContext.getUserId()).thenReturn(userId);

        ContaReceber conta1 = new ContaReceber(UUID.randomUUID(), userId, "Venda freelance", new BigDecimal("1200.00"), LocalDate.of(2026, 5, 23), "Renda");
        ContaReceber conta2 = new ContaReceber(UUID.randomUUID(), userId, "Aluguel recebido", new BigDecimal("2200.00"), LocalDate.of(2026, 5, 25), "Renda");
        when(contaReceberRepository.findByUserId(userId)).thenReturn(List.of(conta1, conta2));

        List<ContaReceberResponse> responses = useCase.listar();

        assertEquals(2, responses.size());
        assertTrue(responses.stream().anyMatch(response -> response.getDescricao().equals("Venda freelance") && response.getValor().compareTo(new BigDecimal("1200.00")) == 0));
        assertTrue(responses.stream().anyMatch(response -> response.getDescricao().equals("Aluguel recebido") && response.getValor().compareTo(new BigDecimal("2200.00")) == 0));
        verify(contaReceberRepository).findByUserId(userId);
    }
}
