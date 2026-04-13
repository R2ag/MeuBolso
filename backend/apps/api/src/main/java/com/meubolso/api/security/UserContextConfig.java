package com.meubolso.api.security;

import com.meubolso.shared.security.UserContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserContextConfig {

    @Bean
    public UserContext userContext() {
        return new UserContext();
    }
}
