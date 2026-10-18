package com.proyecto1.labs.lab2.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.proyecto1.labs.lab2.model.Constans;

/** LAB 2 - Seguridad Spring + JWT (solo para las rutas /Laboratorio1..4). */
@Configuration
public class WebSecurityConfig {

    @Bean("lab2PasswordEncoder")
    public PasswordEncoder lab2PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** Cadena de seguridad de los laboratorios: todo pide token excepto el login. */
    @Bean
    @Order(1)
    public SecurityFilterChain labsSecurityFilterChain(HttpSecurity http,
            JWTAuthtenticationConfig jwtConfig) throws Exception {
        http.securityMatcher(Constans.RUTAS_LABORATORIOS)
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(new JWTAuthorizationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(HttpMethod.POST, Constans.LOGIN_URL).permitAll()
                .anyRequest().authenticated());
        return http.build();
    }

    /**
     * Cadena para el RESTO de la aplicación (Entregas 1, 2...). Al agregar spring-boot-starter-security
     * todo quedaría bloqueado; esta cadena deja todo igual que antes (abierto).
     * Cuando configures la seguridad de la Entrega 2, pon labs.security.permit-rest=false.
     */
    @Bean
    @Order(2)
    @ConditionalOnProperty(name = "labs.security.permit-rest", havingValue = "true", matchIfMissing = true)
    public SecurityFilterChain restoAppSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}
