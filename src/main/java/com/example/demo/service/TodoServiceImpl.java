package com.example.demo.service;

import com.example.demo.constant.Message;
import com.example.demo.model.Todo;
import com.example.demo.model.User;
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

    @Autowired
    private UserService userService;

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

    @Override
    public List<Todo> getTodosByUser(Long userId) {
        logger.debug("todo by user is:");
//        Optional<User> userById = userService.getUserById(userId);
//        if(!userById.isPresent()){
//            throw new RuntimeException("UserId does not exists");
//        }

        List<Todo> todoByUser = todoRepository.findByUserUserId(userId);
        if (todoByUser == null || todoByUser.size() == 0) try {
            throw new Exception(Message.NO_TODO_Present);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return todoByUser;
    }

    @Override
    public List<Todo> getTodosByUserName(String name) {
        List<Todo> todoByUser = todoRepository.findByUserName(name);
        if (todoByUser == null || todoByUser.size() == 0) try {
            throw new Exception(Message.NO_TODO_Present);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return todoByUser;
    }
}

