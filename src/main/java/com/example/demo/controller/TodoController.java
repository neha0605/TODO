package com.example.demo.controller;

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

@RestController
@RequestMapping(value = "/todos")
@CrossOrigin(origins = "*")
public class TodoController {

    @Autowired
    private TodoService todoService;

//    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List<Todo> getAllTodos() {
//        logger.debug("get all todos:");
        return todoService.getAllTodos();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Optional<Todo> getTodoById(@PathVariable Long id) {
//        logger.debug("get todo by id:" + id);
        return todoService.getTodoById(id);
    }


    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    Todo addTodo(@RequestBody Todo todo) {
//        logger.debug("add todo with id:" + todo.getId());
        System.out.println("Reached code here");
        return todoService.addTodo(todo);
    }


    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Todo> updateTodo(@RequestBody Todo todo) {
//        logger.debug("update by id:" + todo.getId());
        if (todo.getId() == null) {
//            logger.debug("Id can not be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Todo updatedTodo = todoService.update(todo);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTodo);
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<?> patchTodo(@RequestBody TodoView view) {

        if (Objects.isNull(view) || Objects.isNull(view.getId())) {
            return ResponseEntity.badRequest().body("Id can not be null");
        }

        if (!view.hasBeenModified()) {
            return ResponseEntity.badRequest().body("No field to patch");
        }

        Optional<Todo> optionalTodo = todoService.getTodoById(view.getId());

        if (!optionalTodo.isPresent()) {
            return ResponseEntity.badRequest().body("Object not found to update.");
        }

        Todo todo = optionalTodo.get();
        view.patch(todo);
        todoService.update(todo);
        return ResponseEntity.ok("resource updated");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable Long id) {
//        logger.debug("delete by id:" + id);
        todoService.deleteById(id);
    }
}
