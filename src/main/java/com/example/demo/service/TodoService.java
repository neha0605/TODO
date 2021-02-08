package com.example.demo.service;

import com.example.demo.model.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoService {

    Optional<Todo> getTodoById(Long id) throws Exception;

    Todo update(Todo todo) throws Exception;

    Todo addTodo(Todo todo);

    void deleteById(Long id) throws Exception;

    List<Todo> getAllTodos() throws Exception;

    List<Todo> getTodosByUser(Long id);

    List<Todo> getTodosByUserName(String name);


}
