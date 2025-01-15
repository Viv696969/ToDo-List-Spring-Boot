package com.vivekempire.TodoList.controllers;

import com.vivekempire.TodoList.utils.TestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



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
}
