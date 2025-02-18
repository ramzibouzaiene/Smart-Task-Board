package com.smarttaskboard.app.exception;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistException extends RuntimeException{
    public ResourceAlreadyExistException(String message){
        super(message);
    }
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }
}
