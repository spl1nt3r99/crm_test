package com.example.crm_test.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.Principal;

@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ErrorResponse handleUnexpectedError(Throwable ex, HttpServletRequest request, Principal principal) {
        log.error("Unexpected error on [{} {}]. Principal: {}",
                request.getMethod(), request.getRequestURI(), principal, ex);
        return ErrorResponse.create(ex, HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
    }
}
