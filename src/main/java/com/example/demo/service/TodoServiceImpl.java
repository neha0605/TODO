package com.example.demo.service;

import com.example.demo.constant.Message;
import com.example.demo.model.Todo;
import com.example.demo.repo.TodoRepository;
import com.example.demo.views.TodoView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Optional<Todo> getTodoById(Long id) throws Exception {
        logger.debug("get by Id:" + id);
        Optional<Todo> todo = todoRepository.findById(id);
        if (!todo.isPresent()) throw new Exception(Message.Id_NOT_EXIST);
        return todo;
    }

    @Override
    public Todo update(Todo todo) throws Exception {
        logger.debug("update todo with id:" + todo.getId());
        Optional<Todo> todoById = todoRepository.findById(todo.getId());
        if (!todoById.isPresent()) throw new Exception(Message.Id_NOT_EXIST);
        return todoRepository.save(todo);
    }

    @Override
    public Todo addTodo(Todo todo) {
        logger.debug("add todo with id:" + todo.getId());
        return todoRepository.save(todo);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        logger.debug("delete todo with id:" + id);
        Todo todo = todoRepository.getOne(id);
        if (todo == null) {
            throw new Exception(Message.Id_NOT_EXIST);
        }
        todoRepository.deleteById(id);
    }

    @Override
    public List<Todo> getAllTodos() throws Exception {
        logger.debug("get todos:");
        List<Todo> allTodos = todoRepository.findAll();
        if (allTodos.size() == 0) {
            throw new Exception(Message.NO_TODO_Present);
        }
        return allTodos;
    }
}

