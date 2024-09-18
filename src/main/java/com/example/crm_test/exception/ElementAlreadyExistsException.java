package com.example.crm_test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

public class ElementAlreadyExistsException extends ErrorResponseException {

    public ElementAlreadyExistsException(String message) {
        super(HttpStatus.CONFLICT, ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, message), null);
    }
}
