package com.example.demo.service;

import com.example.demo.model.Todo;
import com.example.demo.repo.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Optional<Todo> getTodoById(long id) {
        logger.debug("get by Id:" + id);
        return todoRepository.findById(id);
    }

    @Override
    public Todo update(Todo todo) {
        logger.debug("update todo with id:" + todo.getId());
        return todoRepository.save(todo);
    }

    @Override
    public Todo addTodo(Todo todo) {
        logger.debug("add todo with id:" + todo.getId());
        return todoRepository.save(todo);
    }

    @Override
    public void deleteById(Long id) {
        logger.debug("delete todo with id:" + id);
        todoRepository.deleteById(id);
    }

    @Override
    public List<Todo> getAllTodos() {
        logger.debug("get todos:");
        return todoRepository.findAll();
    }
}

