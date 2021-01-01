package com.example.demo.service;

import com.example.demo.model.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoService {

    Optional<Todo> getTodoById(long id);

    Todo update(Todo todo);

    Todo addTodo(Todo todo);

    void deleteById(Long id);

    List<Todo> getAllTodos();
}
