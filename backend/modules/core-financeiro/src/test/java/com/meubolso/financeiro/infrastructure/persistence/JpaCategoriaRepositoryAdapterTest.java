package com.meubolso.financeiro.infrastructure.persistence;

import com.meubolso.financeiro.domain.model.Categoria;
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
        classes = JpaCategoriaRepositoryAdapterTest.TestConfig.class,
        properties = {
                "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
                "spring.datasource.driver-class-name=org.h2.Driver",
                "spring.datasource.username=sa",
                "spring.datasource.password=",
                "spring.jpa.hibernate.ddl-auto=create-drop",
                "spring.jpa.show-sql=false"
        }
)
class JpaCategoriaRepositoryAdapterTest {

    @Autowired
    private JpaCategoriaRepositoryAdapter adapter;

    @Test
    void shouldSaveFindAndDeleteByUserId() {
        Categoria categoria = new Categoria(UUID.randomUUID(), "user-1", "Categoria Teste");

        adapter.save(categoria);

        assertThat(adapter.findByUserId("user-1"))
                .hasSize(1)
                .first()
                .extracting(Categoria::getId)
                .isEqualTo(categoria.getId());

        adapter.deleteByIdAndUserId(categoria.getId(), "user-1");

        assertThat(adapter.findByUserId("user-1")).isEmpty();
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(basePackageClasses = SpringDataCategoriaRepository.class)
    @Import(JpaCategoriaRepositoryAdapter.class)
    static class TestConfig {
    }
}
