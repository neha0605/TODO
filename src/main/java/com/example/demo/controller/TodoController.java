package com.example.demo.controller;

import com.example.demo.constant.Message;
import com.example.demo.model.Todo;
import com.example.demo.service.TodoService;
import com.example.demo.views.TodoView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/todos")
@CrossOrigin(origins = "http://localhost:8080")
public class TodoController {

    @Autowired
    private TodoService todoService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<TodoView>> getAllTodos() {
        logger.debug("get all todos:");
        List<Todo> allTodos;
        try {
            allTodos = todoService.getAllTodos();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        List<TodoView> allTodoView = allTodos.stream().map(t -> t.modelToView()).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(allTodoView);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<TodoView> getTodoById(@PathVariable Long id) {
        logger.debug("get todo by id:" + id);
        Optional<Todo> todo;
        try {
            todo = todoService.getTodoById(id);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(todo.get().modelToView());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TodoView> addTodo(@RequestBody TodoView todoView) {
        logger.info("adding todo:");
        Todo todo = todoView.viewToModel();
        Todo persistedTodo = todoService.addTodo(todo);
        return ResponseEntity.status(HttpStatus.OK).body(persistedTodo.modelToView());
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<TodoView> updateTodo(@RequestBody TodoView todoView) {
        logger.debug("updating todo by id:" + todoView.getId());
        if (Objects.isNull(todoView) || todoView.getId() == null) {
            logger.debug(Message.ID_NOT_NULL);
            return new ResponseEntity(Message.ID_NOT_NULL, HttpStatus.BAD_REQUEST);
        }
        Todo todo = todoView.viewToModel();
        Todo persistedTodo = null;
        try {
            persistedTodo = todoService.update(todo);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return new ResponseEntity(Message.OBJ_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(persistedTodo.modelToView());
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<TodoView> patchTodo(@RequestBody TodoView view) {

        if (Objects.isNull(view) || Objects.isNull(view.getId())) {
            return new ResponseEntity(Message.ID_NOT_NULL, HttpStatus.BAD_REQUEST);
        }
        logger.debug("Patching todo with id:" + view.getId());

        if (!view.hasBeenModified()) {
            return new ResponseEntity(Message.NO_PATCHING_FIELD, HttpStatus.BAD_REQUEST);
        }

        Optional<Todo> optionalTodo = Optional.empty();
        try {
            optionalTodo = todoService.getTodoById(view.getId());
        } catch (Exception e) {
            System.out.println(Message.Id_NOT_EXIST);
        }

        if (!optionalTodo.isPresent()) {
            return new ResponseEntity(Message.OBJ_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }

        Todo todo = optionalTodo.get();
        view.patch(todo);
        Todo persistedTodo;
        try {
            persistedTodo = todoService.update(todo);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return new ResponseEntity(Message.OBJ_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(persistedTodo.modelToView());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        logger.debug("delete by id:" + id);
        try {
            todoService.deleteById(id);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return new ResponseEntity(Message.Id_NOT_EXIST, HttpStatus.BAD_REQUEST);
        }
        logger.debug(Message.RESOURCE_DELETED);
        return new ResponseEntity(Message.RESOURCE_DELETED, HttpStatus.OK);
    }
}
