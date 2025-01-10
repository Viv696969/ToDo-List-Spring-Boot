package com.vivekempire.TodoList.services;

import com.vivekempire.TodoList.dto.req.TodoReqDTO;
import com.vivekempire.TodoList.entities.CustomUser;
import com.vivekempire.TodoList.entities.Todo;
import com.vivekempire.TodoList.repositories.CustomUserRepository;
import com.vivekempire.TodoList.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private CustomUserRepository customUserRepository;

    public ResponseEntity<?> getAllTodos(String userId) {
        Optional<CustomUser> customUser=customUserRepository.findById(userId);

        return ResponseEntity.ok().body(todoRepository.findByCustomUserAndCompletedFalseOrderByUpdatedAtAsc(customUser.get()));
    }

    public void addTodo(TodoReqDTO todoReqDTO,String user_id) {
        CustomUser user=customUserRepository.findById(user_id).get();
        Todo todo=new Todo();
        todo.setBody(todoReqDTO.getBody());
        todo.setTitle(todoReqDTO.getTitle());
        todo.setCustomUser(user);
        todoRepository.save(todo);

    }
}
