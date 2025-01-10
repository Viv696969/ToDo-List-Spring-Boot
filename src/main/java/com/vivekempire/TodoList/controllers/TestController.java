package com.vivekempire.TodoList.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "Server is Up";
    }
}
