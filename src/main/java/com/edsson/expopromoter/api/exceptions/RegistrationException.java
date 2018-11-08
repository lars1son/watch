package com.edsson.expopromoter.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Entity has already exist!")  // 409
public class RegistrationException extends Exception{
    public RegistrationException(){
        super(format("Can not register entity that has already registered!"));
    }
}
