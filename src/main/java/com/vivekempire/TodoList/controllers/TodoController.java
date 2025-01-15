package com.vivekempire.TodoList.controllers;


import com.vivekempire.TodoList.dto.req.TodoReqDTO;
import com.vivekempire.TodoList.dto.resp.TodoRespDTO;
import com.vivekempire.TodoList.entities.Todo;
import com.vivekempire.TodoList.services.TodoService;
import com.vivekempire.TodoList.utils.JWTHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController
{

    @Autowired
    private TodoService todoService;

    @Autowired
    private JWTHelper jwtHelper;

    @GetMapping("/get_all_todos")
    public ResponseEntity<?> getAllTodos(HttpServletRequest request){
        String user_id=(String)request.getAttribute("user_id");
        return todoService.getAllTodos(user_id);
    }

    @PostMapping("/add_todo")
    public ResponseEntity<?> addTodo(@RequestBody TodoReqDTO todoReqDTO,HttpServletRequest request){
        String user_id=(String)request.getAttribute("user_id");
        todoService.addTodo(todoReqDTO, user_id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TodoRespDTO("Todo Added successfully...", true));

    }

    @PostMapping("/mark_as_completed")
    public ResponseEntity<?> markAsCompleted(@RequestParam("id") String todoId){
        todoService.markTodoAsCompleted(todoId);
        return ResponseEntity.status(HttpStatus.OK).body(new TodoRespDTO("Marked Todo as Completed..",true));
    }

    @PostMapping("/delete_todo")
    public ResponseEntity<?> deleteTodo(@RequestParam("id") String todoId){
        todoService.deleteTodo(todoId);
        return ResponseEntity.status(HttpStatus.OK).body(new TodoRespDTO("Deleted Todo Successfully..",true));
    }

    @PostMapping("/show_completed")
    public ResponseEntity<?> showCompletedTodo(HttpServletRequest request){
        String user_id=(String)request.getAttribute("user_id");
        List<Todo>completedTodos=todoService.getCompleted(user_id);
        return ResponseEntity.status(HttpStatus.OK).body(completedTodos);

    }


}
