package com.dex.coreserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class LegalEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<?> handleLegalEnityNotFoundException(LegalEntityNotFoundException exception, WebRequest request) {
        LegalEntityNotFoundException exceptionResponse = new LegalEntityNotFoundException(exception.getMessage());
        return new ResponseEntity<LegalEntityNotFoundException>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
