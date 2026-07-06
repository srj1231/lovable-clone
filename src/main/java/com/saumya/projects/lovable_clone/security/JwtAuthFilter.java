package com.saumya.projects.lovable_clone.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Nonnull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final AuthUtil authUtil;

    /*
     * FilterChain: The sequence of filters that HTTP requests pass through in Spring Security.
     * JwtAuthFilter (custom filter): Validates JWT tokens.
     * SecurityContextFilter - manages security context, etc.
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        log.info("incoming request {}", request.getRequestURI());   // log request for debugging

        final String requestToken = request.getHeader("Authorization");
        if(requestToken == null || !requestToken.startsWith("Bearer ")) {   // validate token presence and format
            filterChain.doFilter(request, response); // pass request to next filter in chain
            return;
        }

        try {
            String token = requestToken.split("Bearer ")[1];
            JwtUserPrinciple user = authUtil.verifyToken(token);

            if(user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        user, // authenticated user object - principal
                        null, // credentials
                        user.authorities() // permissions/roles granted to the user
                );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } catch (Exception e) {
            log.warn("Failed to verify JWT token: {}", e.getMessage());
        }

        filterChain.doFilter(request, response); // pass request to next filter in chain for stateless authentication
    }
}

/*
 * Stateless: the server does not store or maintain any session state about the client between requests.
 * Each request from the client contains all the information (in JWT token) needed to understand and process it.
 * Benefits: Reduced server memory usage, horizontal scalability.
 * Trade-offs: token storage overhead, tokens cannot be easily revoked before expiration.
 */
