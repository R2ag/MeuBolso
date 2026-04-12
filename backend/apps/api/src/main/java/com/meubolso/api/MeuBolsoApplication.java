package com.meubolso.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.meubolso.api", "com.meubolso.financeiro", "com.meubolso.importacao", "com.meubolso.shared"})
public class MeuBolsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeuBolsoApplication.class, args);
    }
}
