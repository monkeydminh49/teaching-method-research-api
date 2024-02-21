package com.minhdunk.research.component;


import com.minhdunk.research.exception.EmailNotVerifiedException;
import com.minhdunk.research.exception.NotFoundException;
import com.minhdunk.research.service.JwtService;
import com.minhdunk.research.service.UserInfoDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtAuthenticationFilter  extends OncePerRequestFilter  {
    @Autowired
    public JwtService jwtService;
    @Autowired
    public UserInfoDetailsService userDetailsService;
    private  List<AntPathRequestMatcher> permitAllRequestMatchers;

    private final List<String> permitAllPaths = Arrays.asList(
            "/api/v1/hello",
            "/api/v1/register",
            "/api/v1/login",
            "/api/v1/refresh-token",
            "/api/v1/auth/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/api/v1/media/**",
//            "/api/v1/documents/**",
            "/api/v1/verify-email/**"
//            "/api/v1/send-verification-email"
    );

    public JwtAuthenticationFilter(List<String> permitAllPaths) {
        super();
        this.permitAllRequestMatchers = permitAllPaths.stream()
                .map(AntPathRequestMatcher::new)
                .collect(Collectors.toList());
    }


    @Override
    protected void doFilterInternal (
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        log.info("Request" + request);
        log.info("permitAllPaths: " + permitAllRequestMatchers.size());
        log.info("shouldNotFilter" + permitAllRequestMatchers.stream()
                .anyMatch(matcher -> matcher.matches(request)));

        if (permitAllRequestMatchers.isEmpty()){
            this.permitAllRequestMatchers = this.permitAllPaths.stream()
                    .map(AntPathRequestMatcher::new)
                    .collect(Collectors.toList());
        }

        boolean shouldNotFilter = permitAllRequestMatchers.stream()
                .anyMatch(matcher -> matcher.matches(request));

        boolean sendVerificationEmail = request.getRequestURI().equals("/api/v1/send-verification-email");

        if (authHeader == null || !authHeader.startsWith("Bearer ") || shouldNotFilter || authHeader.startsWith("Bearer null")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);

        String username = null;
        try {
            username = jwtService.extractUsername(jwt);
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("message", e.getMessage());
        }



        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails =  this.userDetailsService.loadUserByUsername(username);
            UserInfoUserDetails user = (UserInfoUserDetails) userDetails;
            if (jwtService.isTokenValid(jwt, userDetails) ){

//                System.out.println("username: " + username);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                if (!sendVerificationEmail && !user.getUser().getEnabled()) {
                    log.info("Email not verified");
                    response.setStatus(HttpStatus.valueOf(409).value());
                    response.setHeader("message", "Email not verified");
                    response.sendError(HttpStatus.valueOf(409).value(), "Email not verified");
                    response.setHeader("message", "Email not verified");
                }
            }

        }

        filterChain.doFilter(request, response);

    }
}
