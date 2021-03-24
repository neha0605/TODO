package com.example.demo.views;

import com.example.demo.model.Todo;
import com.example.demo.model.User;

import java.util.Date;
import java.util.Objects;

public class TodoView {

    private Long id;
    private String description;
    private Date targetDate;
    private Boolean isDone;
    private User user;

    public TodoView() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public Boolean isDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public Boolean getDone() {
        return isDone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean hasBeenModified() {
        return Objects.nonNull(this.getTargetDate()) ||
                Objects.nonNull(this.isDone()) ||
                Objects.nonNull(this.getDescription());
    }

    public void patch(Todo todo) {
        if (Objects.nonNull(this.getDescription())) {
            todo.setDescription(this.getDescription());
        }
        if (Objects.nonNull(this.getTargetDate())) {
            todo.setTargetDate(this.getTargetDate());
        }
        if (Objects.nonNull(this.isDone())) {
            todo.setDone(this.isDone());
        }
    }


    public Todo viewToModel() {
        Todo todo = new Todo();
        todo.setId(this.id);
        todo.setDescription(this.description);
        todo.setTargetDate(this.targetDate);
        todo.setDone(this.isDone);
        return todo;
    }


    @Override
    public String toString() {
        return "TodoView{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", targetDate=" + targetDate +
                ", isDone=" + isDone +
                '}';
    }
}
