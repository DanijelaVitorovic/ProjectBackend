package com.dex.coreserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserEmailException extends RuntimeException{
    public UserEmailException(String message) {
        super(message);
    }
}
