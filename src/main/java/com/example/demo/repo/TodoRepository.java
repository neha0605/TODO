package com.example.demo.repo;


import com.example.demo.model.Todo;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    Optional<Todo> findById(Long id);

    List<Todo> findAll();

    Todo save(Todo todo);

    void deleteById(Long id);

    Todo getOne(Long id);

    List<Todo> findByUser(User userId);

    List<Todo> findByUserUserId(Long userId);

    List<Todo> findByUserName(String user);


}
