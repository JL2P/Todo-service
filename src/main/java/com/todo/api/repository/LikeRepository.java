package com.todo.api.repository;

import com.todo.api.domain.Like;
import com.todo.api.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByTodoAndAccountId(Todo todo, String accountId);
}
