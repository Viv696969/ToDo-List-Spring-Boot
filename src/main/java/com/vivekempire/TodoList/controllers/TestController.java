package com.vivekempire.TodoList.controllers;


import com.vivekempire.TodoList.dto.resp.ErrorResponse;
import com.vivekempire.TodoList.exceptions.TodoCustomException;
import com.vivekempire.TodoList.utils.TestUtility;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;


@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestUtility testUtility;

    @GetMapping("/test")
    public String test() throws InterruptedException {
        System.out.println("The Non-Async Method "+Thread.currentThread().getName());
        testUtility.testMethod();
        return "Server is Up...";
    }

    @PostMapping("/send_data")
    public String sendData(@RequestBody Object object){
        System.out.println(object);
        return "OK";
    }

    @GetMapping("/test_exception")
    public ResponseEntity<?> testException(){
        throw new TodoCustomException("Did not get data from Elastic Search", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TodoCustomException.class)
    public ResponseEntity<?> handleException(HttpServletResponse resp, TodoCustomException exception) {
        ErrorResponse response = new ErrorResponse(exception.getMessage(),exception.getStatus(),new Date());
        return new ResponseEntity<>(response,exception.getStatus() );
    }
}
