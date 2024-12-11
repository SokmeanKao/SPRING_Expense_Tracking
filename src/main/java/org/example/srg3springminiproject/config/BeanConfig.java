package org.example.srg3springminiproject.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @org.springframework.context.annotation.Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
