package com.example.demo.repo;

import com.example.demo.model.Todo;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    @Order(2)
    void findById() {
        Optional<Todo> persistedTodo = todoRepository.findById(1l);
        Assert.assertNotNull(persistedTodo);
        Assert.assertTrue(persistedTodo.isPresent());
    }

    @Test
    @Order(3)
    void testFindAllTodos() {
        List<Todo> allTodos = todoRepository.findAll();
        assertNotNull(allTodos);
        assertTrue(allTodos.size() == 1);
    }

    @Test
    @Order(1)
    void testSavetodo() {
        Todo todo = new Todo();
        todo.setDescription("hello world");
        todo.setTargetDate(new Timestamp(new Date().getTime()));
        todo.setDone(false);
        todoRepository.save(todo);
        Optional<Todo> testTodo = todoRepository.findById(todo.getId());
        assertNotNull(testTodo);
        assertEquals(testTodo.get().getDescription(), todo.getDescription());
        assertEquals(testTodo.get().getTargetDate(), todo.getTargetDate());
    }

    @Test
    @Order(4)
    void testDeleteTodoById() {
        todoRepository.deleteById(1l);
        Optional<Todo> byId = todoRepository.findById(1l);
        assertFalse(byId.isPresent());
    }
}