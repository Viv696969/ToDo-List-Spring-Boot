package com.vivekempire.TodoList.exceptions;


import com.vivekempire.TodoList.dto.resp.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TodoCustomException.class)
    public ResponseEntity<?> sendErrorMessage(TodoCustomException exception){
        ErrorResponse response=new ErrorResponse(exception.getMessage(), exception.getStatus(),new Date());
        return  new ResponseEntity<>(response,exception.getStatus());
    }
}
