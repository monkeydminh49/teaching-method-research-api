package com.minhdunk.research.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component("customAuthenticationEntryPoint")
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
//        ObjectMapper mapper = new ObjectMapper();
////        ErrorDetails errorDetails = ErrorDetails.builder()
////                .details(String.valueOf(e.getClass()))
////                .message("JWT has expired")
////                .timestamp(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss", Locale.ENGLISH)
////                        .format(LocalDateTime.now()))
////                .build();
//        System.out.println(e.getClass()     );
//        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
//        httpServletResponse.setContentType("application/json");
//        httpServletResponse.setCharacterEncoding("UTF-8");
//        httpServletResponse.getWriter().write(mapper.writeValueAsString("error"));
    }

}
