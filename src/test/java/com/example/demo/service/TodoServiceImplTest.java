package com.example.demo.service;

import com.example.demo.model.Todo;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TodoServiceImplTest {

    @Autowired
    private TodoService todoService;

    @Test
    @Order(1)
    void addTodo() {
        Todo todo = new Todo();
        todo.setDescription("service todo");
        todo.setTargetDate(new Timestamp(new Date().getTime()));
        todo.setDone(false);
        Todo addedTodo = todoService.addTodo(todo);
        Assert.assertNotNull(addedTodo);
        Assert.assertEquals(addedTodo, todo);
    }


    @Test
    @Order(2)
    void getTodoById() {
        Optional<Todo> todo = todoService.getTodoById(1);
        Assert.assertNotNull(todo);
    }

    @Test
    @Order(4)
    void update() {
        Todo todo = new Todo();
        todo.setDescription("service update todo");
        todo.setTargetDate(new Timestamp(new Date().getTime()));
        todo.setDone(false);
        todoService.addTodo(todo);
        todo.setDone(true);
        Todo updatedTodo = todoService.update(todo);
        Assert.assertNotNull(updatedTodo);
        Assert.assertTrue(updatedTodo.isDone() == true);
    }


    @Test
    @Order(5)
    void deleteById() {
        todoService.deleteById(1l);
        Optional<Todo> todoById = todoService.getTodoById(1);
        Assert.assertFalse(todoById.isPresent());
    }

    @Test
    @Order(3)
    void getAllTodos() {
        List<Todo> allTodos = todoService.getAllTodos();
        Assert.assertTrue(allTodos.size() == 1);
    }
}