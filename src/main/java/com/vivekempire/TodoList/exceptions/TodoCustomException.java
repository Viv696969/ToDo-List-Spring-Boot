package com.vivekempire.TodoList.exceptions;

import org.springframework.http.HttpStatus;

public class TodoCustomException extends RuntimeException{

    private String message;
    private HttpStatus status;

    public TodoCustomException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }


    public HttpStatus getStatus() {
        return status;
    }



}
