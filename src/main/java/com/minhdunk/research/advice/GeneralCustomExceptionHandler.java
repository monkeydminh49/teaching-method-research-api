package com.minhdunk.research.advice;

import com.minhdunk.research.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class GeneralCustomExceptionHandler extends ResponseEntityExceptionHandler {
//    @Override
//    public ResponseEntity<Object> handleHttpMessageNotReadable(
//            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        ProblemDetail body = createProblemDetail(ex, status, "Missing request body", null, null, request);
//        return handleExceptionInternal(ex, body, headers, status, request);
//    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail handleNotFoundException(NotFoundException ex) {
        log.info("Handle not found exception");
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }
}