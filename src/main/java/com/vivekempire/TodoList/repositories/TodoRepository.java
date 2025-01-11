package com.vivekempire.TodoList.repositories;


import com.vivekempire.TodoList.entities.CustomUser;
import com.vivekempire.TodoList.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo,String> {

    List<Todo> findByCustomUserAndCompletedFalseOrderByUpdatedAtAsc(CustomUser customUser);
    List<Todo> findByCustomUserAndCompletedTrueOrderByUpdatedAtDesc(CustomUser customUser);
}
