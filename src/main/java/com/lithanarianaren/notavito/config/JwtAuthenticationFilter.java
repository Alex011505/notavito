package com.lithanarianaren.notavito.config;

import com.lithanarianaren.notavito.service.JSONWebTokenService;
import java.io.IOException;
import java.util.Collections;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JSONWebTokenService jsonWebTokenService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            try {
                String username = jsonWebTokenService.getUsername(token);
                String role = jsonWebTokenService.getRole(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (jsonWebTokenService.validateToken(token, username)) {
                        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                username, null, Collections.singletonList(authority));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } catch (Exception e) {
                System.err.println("JWT Token validation failed: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

}
