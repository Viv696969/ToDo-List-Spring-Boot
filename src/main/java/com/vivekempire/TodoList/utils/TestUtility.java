package com.vivekempire.TodoList.utils;


import org.springframework.scheduling.annotation.Async;

import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;


@Component
public class TestUtility {

    @Async
    public void testMethod() throws InterruptedException {
        System.out.println("In the Async Thread "+Thread.currentThread().getName());
        Thread.sleep(Duration.ofSeconds(10).toMillis());

    }
}
