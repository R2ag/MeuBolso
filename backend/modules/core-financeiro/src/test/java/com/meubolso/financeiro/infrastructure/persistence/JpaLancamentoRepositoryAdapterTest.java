package com.meubolso.financeiro.infrastructure.persistence;

import com.meubolso.financeiro.domain.model.Lancamento;
import com.meubolso.financeiro.domain.model.LancamentoStatus;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = JpaLancamentoRepositoryAdapterTest.TestConfig.class,
        properties = {
                "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
                "spring.datasource.driver-class-name=org.h2.Driver",
                "spring.datasource.username=sa",
                "spring.datasource.password=",
                "spring.jpa.hibernate.ddl-auto=create-drop",
                "spring.jpa.show-sql=false"
        }
)
class JpaLancamentoRepositoryAdapterTest {

    @Autowired
    private JpaLancamentoRepositoryAdapter adapter;

    @Test
    void shouldSaveAndFindByUserId() {
        Lancamento lancamento = new Lancamento(
                UUID.randomUUID(),
                "user-1",
                "Pagamento de teste",
                new BigDecimal("123.45"),
                LocalDate.now(),
                "Conta Teste",
                "Categoria Teste",
                LancamentoStatus.CONFIRMADO
        );

        adapter.save(lancamento);

        assertThat(adapter.findByUserId("user-1"))
                .hasSize(1)
                .first()
                .extracting(Lancamento::getId)
                .isEqualTo(lancamento.getId());
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(basePackageClasses = SpringDataLancamentoRepository.class)
    @Import(JpaLancamentoRepositoryAdapter.class)
    static class TestConfig {
    }
}
