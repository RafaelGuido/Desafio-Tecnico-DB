package com.desafio.desafiotecnicodb.core.config;

import com.desafio.desafiotecnicodb.domain.service.ReceitaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ReceitaService receitaService() {
        return new ReceitaService();
    }
}
