package com.example.demo.controller;

import com.example.demo.model.Todo;
import org.apache.http.impl.client.HttpClientBuilder;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TodoControllerTest {

    public static final String LOCAL = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void addTodo() {
        Todo todo = new Todo();
        todo.setDescription("my new todo");
        todo.setTargetDate(new Timestamp(new Date().getTime()));
        todo.setDone(false);
        Todo response = restTemplate.postForObject(LOCAL + port + "/todos", todo, Todo.class);
        Assert.assertNotNull(response);
        Assert.assertEquals((long) response.getId(), 1);
    }

    @Test
    @Order(2)
    void getAllTodos() {
        int todosCount = restTemplate
                .getForObject(LOCAL + port + "/todos", List.class).size();
        assertTrue(todosCount == 1);
    }

    @Test
    @Order(3)
    void getTodoById() {
        Todo todo = restTemplate
                .getForObject(LOCAL + port + "/todos/1", Todo.class);
        Assert.assertNotNull(todo);
    }


    @Test
    @Order(4)
    void updateTodo() {
        Todo todo = restTemplate
                .getForObject(LOCAL + port + "/todos/1", Todo.class);
        todo.setDone(true);
        restTemplate.put(LOCAL + port + "/todos", todo);

        Todo updatedTodo = restTemplate
                .getForObject(LOCAL + port + "/todos/1", Todo.class);
        Assert.assertNotNull(updatedTodo);
        Assert.assertTrue(updatedTodo.isDone());
    }

    @Test
    @Order(5)
    void patchTodo() {
        Todo response = restTemplate.getForObject(LOCAL + port + "/todos/1", Todo.class);
        Assert.assertNotNull(response);
        Assert.assertFalse(response.isDone());

        Todo patchObj = new Todo();
        patchObj.setId(response.getId());
        patchObj.setDone(true);

        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build()));
        String result = restTemplate.patchForObject(LOCAL + port + "/todos", patchObj, String.class);
        Assert.assertNotNull(result);
        Assert.assertEquals(result, "resource updated");

        response = restTemplate.getForObject(LOCAL + port + "/todos/1", Todo.class);
        Assert.assertNotNull(response);
        Assert.assertTrue(response.isDone());
    }

    @Test
    @Order(6)
    void deleteById() {
        Todo todo = restTemplate
                .getForObject(LOCAL + port + "/todos/1", Todo.class);
        Assert.assertNotNull(todo);
        restTemplate.delete(LOCAL + port + "/todos/1");
        try {
            todo = restTemplate.getForObject(LOCAL + port + "/todos/1", Todo.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}