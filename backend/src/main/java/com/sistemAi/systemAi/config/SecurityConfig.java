package com.sistemAi.systemAi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // Disabilita CSRF se non necessario
            .authorizeRequests()
            .requestMatchers("/**").permitAll()  // Permetti l'accesso a tutte le richieste
            .anyRequest().permitAll();  // Permetti anche altre richieste

        return http.build();
    }
}
