package com.todo.api.repository;

import com.todo.api.domain.Todo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByWriter(String writer, Sort sort);

    List<Todo> findAllByWriterIn(List<String> writer ,Sort sort);
}
