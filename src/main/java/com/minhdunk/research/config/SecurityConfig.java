package com.minhdunk.research.config;


import com.minhdunk.research.component.JwtAuthenticationFilter;
import com.minhdunk.research.service.UserInfoDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtFilter;

    @Autowired
    @Qualifier("customAuthenticationEntryPoint")
    AuthenticationEntryPoint authEntryPoint;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(
                                        "/api/v1/hello",
                                        "/api/v1/register",
                                        "/api/v1/login",
                                        "/api/v1/refresh-token",
                                        "/api/v1/auth/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/api/v1/media/**",
                                        "/api/v1/documents/**",
                                        "/api/v1/verify-email/**"
                                )
                                .permitAll()
                                .requestMatchers("/api/v1/user/**",
                                        "api/v1/users/**",
                                        "/api/v1/send-verification-email/**",
                                        "/api/v1/send-verification-email",
                                        "/api/v1/hello-**",
                                        "/api/v1/classrooms/**",
                                        "/api/v1/assignments/**",
                                        "/api/v1/posts/**",
                                        "/api/v1/comments/**",
                                        "/api/v1/notifications/**",
                                        "/api/v1/groups/**"
                                )
                                .authenticated()
                )
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
//                .exceptionHandling((exceptionHandling)-> exceptionHandling.authenticationEntryPoint(authEntryPoint))
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling.authenticationEntryPoint((request, response, authException) -> {
                            System.out.println(Arrays.toString(authException.getStackTrace()));
                            response.setContentType("application/json");
                            if (response.getStatus() != HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            }
                            response.getWriter().write(new JSONObject()
                                    .put("timestamp", LocalDateTime.now())
                                    .put("message", (response.getHeader("message") != null ? response.getHeader("message") : authException.getMessage()))
                                    .put("status", response.getStatus())
                                    .put("data", authException.getCause())
                                    .toString());
                        }))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

//    @Bean
//    public JwtAuthenticationFilter myJwtFilter() {
//        // Define the list of paths to permit all
//        List<String> permitAllPaths = List.of(
//                "/api/v1/hello",
//                "/api/v1/register",
//                "/api/v1/login",
//                "/api/v1/refresh-token",
//                "/api/v1/auth/**",
//                "/v3/api-docs/**",
//                "/swagger-ui/**",
//                "/api/v1/media/**",
//                "/api/v1/documents/**"
//        );
//        return new JwtAuthenticationFilter(permitAllPaths);
//    @Bean
//    public CorsWebFilter corsWebFilter() {
//        return new CorsWebFilter(corsConfiguration());
//    }
//    }
}
