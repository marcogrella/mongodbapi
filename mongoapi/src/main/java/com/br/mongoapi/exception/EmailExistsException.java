package com.br.mongoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EmailExistsException extends RuntimeException{

    public EmailExistsException(String message){
        super(message);
    }
}