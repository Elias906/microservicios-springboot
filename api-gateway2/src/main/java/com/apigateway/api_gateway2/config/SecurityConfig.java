/*package com.apigateway.api_gateway2.config;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers(HttpMethod.GET, "/v1/csrf").permitAll()
                .requestMatchers(HttpMethod.POST, "/login", "/api/login").permitAll()  // Permitir acceso sin autenticación a /login y /api/login
                .anyRequest().authenticated()  // Requiere autenticación para cualquier otra ruta
                .and()
                .httpBasic().disable();

        return http.build();
    }

}
*/