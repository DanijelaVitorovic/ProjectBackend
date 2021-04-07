package com.dex.coreserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LegalEntityNotFoundException extends  RuntimeException{

    public LegalEntityNotFoundException(String message) {
        super(message);
    }
}
