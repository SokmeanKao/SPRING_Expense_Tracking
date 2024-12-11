package org.example.srg3springminiproject.config;

import org.apache.ibatis.type.TypeHandlerRegistry;
import org.example.srg3springminiproject.typehandler.UuidTypeHandler;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.UUID;

@Configuration
public class UuidMyBatisConfig {

    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            typeHandlerRegistry.register(UUID.class, new UuidTypeHandler());
        };
    }
}


