package com.proyecto1.labs.lab2.config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.proyecto1.labs.lab2.model.Constans;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * LAB 2 - Lee el header Authorization: Bearer <token> y deja autenticado al usuario.
 * OJO: NO lleva @Component a propósito; se crea dentro de WebSecurityConfig
 * para que solo actúe en las rutas de los laboratorios.
 */
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final JWTAuthtenticationConfig jwtConfig;

    public JWTAuthorizationFilter(JWTAuthtenticationConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        String header = request.getHeader(Constans.HEADER_AUTHORIZATION_KEY);

        if (header == null || !header.startsWith(Constans.TOKEN_BEARER_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String token = header.substring(Constans.TOKEN_BEARER_PREFIX.length());
            Claims claims = jwtConfig.validarToken(token);

            @SuppressWarnings("unchecked")
            List<String> authorities = (List<String>) claims.get("authorities");
            List<GrantedAuthority> granted = authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(claims.getSubject(), null, granted);
            SecurityContextHolder.getContext().setAuthentication(auth);
            chain.doFilter(request, response);

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token inválido o vencido");
        }
    }
}
