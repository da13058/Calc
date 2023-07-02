package org.example.exceptions;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    public ValidationException(String exceptionMessage){
        super(exceptionMessage);
    }

}
