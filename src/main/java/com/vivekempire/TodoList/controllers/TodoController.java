package com.vivekempire.TodoList.controllers;


import com.vivekempire.TodoList.dto.req.TodoReqDTO;
import com.vivekempire.TodoList.dto.resp.TodoRespDTO;
import com.vivekempire.TodoList.services.TodoService;
import com.vivekempire.TodoList.utils.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/todo")
public class TodoController
{

    @Autowired
    private TodoService todoService;

    @Autowired
    private JWTHelper jwtHelper;

    @GetMapping("/get_all_todos")
    public ResponseEntity<?> getAllTodos(@RequestHeader("Authorization") String token){
        Map<String,Object> authorizationHashMap=jwtHelper.isValid(token);
        if ((boolean)authorizationHashMap.get("status"))
            return todoService.getAllTodos((String) authorizationHashMap.get("user_id"));
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();


    }

    @PostMapping("/add_todo")
    public ResponseEntity<?> addTodo(@RequestBody TodoReqDTO todoReqDTO,@RequestHeader("Authorization") String token){

        Map<String,Object> authorizationHashMap=jwtHelper.isValid(token);
        if ((boolean)authorizationHashMap.get("status")) {
            todoService.addTodo(todoReqDTO, (String) authorizationHashMap.get("user_id"));
            return ResponseEntity.status(HttpStatus.CREATED).body(new TodoRespDTO("Todo Added successfully...", true));
        }
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}
