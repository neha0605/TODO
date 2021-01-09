package com.example.demo.service;

import com.example.demo.model.Todo;
import com.example.demo.repo.TodoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TodoServiceImplTest extends BaseTestCase{

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    @Test
    @Order(1)
    public void addTodo() {
        Todo todo = new Todo();
        todo.setDescription("service todo");
        todo.setTargetDate(new Timestamp(new Date().getTime()));
        todo.setDone(false);
        Mockito.when(todoRepository.save(todo)).thenReturn(updateId(todo));

        Todo addedTodo = todoService.addTodo(todo);

        Assert.assertNotNull(addedTodo);
        Assert.assertEquals(addedTodo, todo);
        Assert.assertNotNull(todo.getId());
        Assert.assertTrue(todo.getId() == 1L);

        Mockito.verify(todoRepository, Mockito.times(1)).save(todo);
    }


    @Test
    @Order(2)
    public void getTodoById() {
        Todo todo = new Todo();
        todo.setDescription("service update todo");
        todo.setTargetDate(new Timestamp(new Date().getTime()));
        todo.setDone(false);
        Mockito.when(todoRepository.findById(1l)).thenReturn(Optional.of(updateId(todo)));
        try {
            Optional<Todo> todoById = todoService.getTodoById(1l);
            Assert.assertNotNull(todoById);
            Assert.assertEquals("service update todo", todoById.get().getDescription());
            Mockito.verify(todoRepository, Mockito.times(1)).findById(todoById.get().getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(4)
    public void update() {
        Todo todo = new Todo();
        todo.setDescription("service update todo");
        todo.setTargetDate(new Timestamp(new Date().getTime()));
        todo.setDone(false);

        //Mockito.when(todoRepository.save(todo)).thenReturn(todo);

        Todo updatedTodo = null;
        try {
            updatedTodo = todoService.update(todo);
            Assert.assertNotNull(updatedTodo);
            Mockito.verify(todoRepository, Mockito.times(1)).save(updatedTodo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(5)
    public void deleteById() {
        Todo todo = new Todo();
        todo.setDescription("service delete todo");
        todo.setTargetDate(new Timestamp(new Date().getTime()));
        todo.setDone(false);
        try {
            todoService.deleteById(updateId(todo).getId());
            Mockito.verify(todoRepository, Mockito.times(1)).deleteById(todo.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    @Order(3)
    public void getAllTodos() {
        Todo todo = new Todo();
        todo.setDescription("service update todo");
        todo.setTargetDate(new Timestamp(new Date().getTime()));
        todo.setDone(false);

        List<Todo> expectedTodo = Arrays.asList(updateId(todo));

        Mockito.doReturn(expectedTodo).when(todoRepository).findAll();

        try {
            List<Todo> allTodos = todoService.getAllTodos();
            Assert.assertNotNull(allTodos);
            Assert.assertTrue(allTodos.size() == 1);
            Assert.assertEquals(expectedTodo, allTodos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNullReturnIfNoDataFound() {
        List allTodos = new ArrayList();
        Mockito.when(todoRepository.findAll()).thenReturn(allTodos);
        try {
            List<Todo> todos = todoService.getAllTodos();
            Mockito.verify(todoRepository).findAll();
            Assert.assertEquals(null, todos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Todo updateId(Todo todo) {
        todo.setId(1L);
        return todo;
    }
}