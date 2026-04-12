package com.meubolso.financeiro.infrastructure.persistence;

import com.meubolso.financeiro.domain.model.Conta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = JpaContaRepositoryAdapterTest.TestConfig.class,
        properties = {
                "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
                "spring.datasource.driver-class-name=org.h2.Driver",
                "spring.datasource.username=sa",
                "spring.datasource.password=",
                "spring.jpa.hibernate.ddl-auto=create-drop",
                "spring.jpa.show-sql=false"
        }
)
class JpaContaRepositoryAdapterTest {

    @Autowired
    private JpaContaRepositoryAdapter adapter;

    @Test
    void shouldSaveFindAndDeleteByUserId() {
        Conta conta = new Conta(UUID.randomUUID(), "user-1", "Conta Teste");

        adapter.save(conta);

        assertThat(adapter.findByUserId("user-1"))
                .hasSize(1)
                .first()
                .extracting(Conta::getId)
                .isEqualTo(conta.getId());

        adapter.deleteByIdAndUserId(conta.getId(), "user-1");

        assertThat(adapter.findByUserId("user-1")).isEmpty();
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(basePackageClasses = SpringDataContaRepository.class)
    @Import(JpaContaRepositoryAdapter.class)
    static class TestConfig {
    }
}
