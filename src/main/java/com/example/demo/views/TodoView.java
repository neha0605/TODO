package com.example.demo.views;

import com.example.demo.model.Todo;

import java.util.Date;
import java.util.Objects;

public class TodoView {

    private Long id;
    private String description;
    private Date targetDate;
    private Boolean isDone;

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

    public boolean hasBeenModified(){
        return Objects.nonNull(this.getTargetDate()) ||
                Objects.nonNull(this.isDone()) ||
                Objects.nonNull(this.getDescription());
    }

    public void patch(Todo todo){
        if(Objects.nonNull(this.getDescription())){
            todo.setDescription(this.getDescription());
        }
        if(Objects.nonNull(this.getTargetDate())){
            todo.setTargetDate(this.getTargetDate());
        }
        if(Objects.nonNull(this.isDone())){
            todo.setDone(this.isDone());
        }
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
