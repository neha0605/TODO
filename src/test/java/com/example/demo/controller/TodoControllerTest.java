package com.example.demo.controller;

import com.example.demo.constant.Message;
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
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        HttpHeaders headers = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<Todo> responseTodo = restTemplate
                .getForEntity(LOCAL + port + "/todos/1", Todo.class);
        Assert.assertNotNull(responseTodo.getBody());
        Assert.assertEquals(HttpStatus.OK, responseTodo.getStatusCode());
        Assert.assertEquals(1l, (long) responseTodo.getBody().getId());
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
        Assert.assertTrue(response.isDone());

        Todo patchObj = new Todo();
        patchObj.setId(response.getId());
        patchObj.setDone(false);

        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build()));
        String result = restTemplate.patchForObject(LOCAL + port + "/todos", patchObj, String.class);
        Assert.assertNotNull(result);

        response = restTemplate.getForObject(LOCAL + port + "/todos/1", Todo.class);
        Assert.assertNotNull(response);
        Assert.assertFalse(response.isDone());
    }

    @Order(6)
    @Test
    void deleteById() {
        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
        HttpEntity httpEntity = new HttpEntity(headers);

        ResponseEntity<Todo> responseTodo = restTemplate.getForEntity(LOCAL + port + "/todos/1", Todo.class);
        Assert.assertNotNull(responseTodo);
        Assert.assertEquals(1l, (long) responseTodo.getBody().getId());

        ResponseEntity<String> response = restTemplate.exchange(LOCAL + port + "/todos/1", HttpMethod.DELETE, httpEntity, String.class);
        Assert.assertEquals(response.getBody(), "resource deleted");
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

        ResponseEntity<String> message = restTemplate.getForEntity(LOCAL + port + "/todos/1", String.class);
        assertNotNull(message);
        assertEquals(Message.Id_NOT_EXIST, message.getBody());
    }
}