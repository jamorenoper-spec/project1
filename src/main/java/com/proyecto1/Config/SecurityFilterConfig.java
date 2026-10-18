package com.proyecto1.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.proyecto1.Repository.UsuarioRepository;
import com.proyecto1.Security.ApiKeyAuthFilter;

@Configuration
public class SecurityFilterConfig {

    @Autowired
    @Qualifier("IUsuarioRepo")
    private UsuarioRepository usuarioRepository;

    // Protege los endpoints administrativos de cada recurso (Fase 1, Fase 2, la creacion y
    // consultas de trayecto de Fase 3, y el cargue de Fase 4). Los servicios publicos (bajo
    // "/publico/") quedan exentos via shouldNotFilter, al igual que el login y el alta de la
    // primera persona (bootstrap: todavia no existe ningun apikey la primera vez que se usa
    // el sistema).
    @Bean
    public FilterRegistrationBean<ApiKeyAuthFilter> apiKeyAuthFilter() {
        FilterRegistrationBean<ApiKeyAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new ApiKeyAuthFilter(usuarioRepository));
        registration.addUrlPatterns(
                "/proyecto1/vehiculo", "/proyecto1/vehiculo/*",
                "/proyecto1/documento", "/proyecto1/documento/*",
                "/proyecto1/persona", "/proyecto1/persona/*",
                "/proyecto1/usuario", "/proyecto1/usuario/*",
                "/proyecto1/vehiculo-conductor", "/proyecto1/vehiculo-conductor/*",
                "/proyecto1/trayecto", "/proyecto1/trayecto/consulta", "/proyecto1/trayecto/consulta/*",
                "/proyecto1/trayecto/cargue", "/proyecto1/trayecto/cargue/*");
        registration.setOrder(1);
        return registration;
    }
}
