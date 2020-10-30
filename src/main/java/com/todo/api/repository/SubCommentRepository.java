package com.todo.api.repository;

import com.todo.api.domain.SubComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCommentRepository extends JpaRepository<SubComment, Long> {
}
