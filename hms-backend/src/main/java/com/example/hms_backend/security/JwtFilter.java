package com.example.hms_backend.security;

import com.example.hms_backend.authentication.jwt.JWTService;
import com.example.hms_backend.handler.GenericResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{

            String path = request.getRequestURI();
            AntPathMatcher matcher = new AntPathMatcher();

            // ✅ Bypass JWT filter for public endpoints
            if (matcher.match("/api/auth/**", path)
                    || matcher.match("/api/forgot-password/**", path)
                    || matcher.match("/api/register/form-data", path)
                    || matcher.match("/api/department/**", path)
                    || matcher.match("/api/patients/all", path)
                    || path.equals("/api/data/states")
                    || path.equals("/ping"))
                {
                filterChain.doFilter(request, response);
                return;

            }


            String authHeader = request.getHeader("Authorization");

            // Authorization = Bearer hgvjkljhvbk.hgcjvbknhghvbk.hgjvbkjghv
            String token = null;
            String username = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtService.extractUsername(token);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                Boolean validateToken = jwtService.validateToken(token, userDetails);
                if (validateToken) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    System.out.println("Principal set in context: " + auth.getPrincipal());
                    System.out.println("Authorities set in context: " + auth.getAuthorities());


                }
            }
        }
        catch (Exception e) {
            generateResponseError(response, e);
            return;
        }
        filterChain.doFilter(request, response);
    }
    private void generateResponseError(HttpServletResponse response, Exception e) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Object error = GenericResponse.builder().status("failed").message(e.getMessage())
                .responseStatus(HttpStatus.UNAUTHORIZED).build().create().getBody();
        response.getWriter().write(new ObjectMapper().writeValueAsString(error));

    }
}
