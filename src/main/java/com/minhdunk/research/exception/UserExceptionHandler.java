package com.minhdunk.research.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UserAlreadyExistedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, ApiException> handleUserAlreadyExistedException(UserAlreadyExistedException ex) {
        return Map.of("error", new ApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, ApiException> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return Map.of("error", new ApiException(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
    }
}
