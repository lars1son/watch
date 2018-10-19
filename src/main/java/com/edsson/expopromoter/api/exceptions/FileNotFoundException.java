package com.edsson.expopromoter.api.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "File not found!")  // 404
public class FileNotFoundException extends Exception {


    public FileNotFoundException() {

            super(format("File not found!"));
    }

}
