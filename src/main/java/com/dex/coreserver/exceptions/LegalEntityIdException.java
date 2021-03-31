package com.dex.coreserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LegalEntityIdException extends RuntimeException{

    public LegalEntityIdException(String message) {
        super(message);
    }
}
