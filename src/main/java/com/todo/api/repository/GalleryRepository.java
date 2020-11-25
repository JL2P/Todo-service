package com.todo.api.repository;

import com.todo.api.domain.Gallery;
import com.todo.api.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {

    Optional<Gallery> findByTodo(Todo todo);
}
