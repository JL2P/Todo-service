package com.todo.api.repository;

import com.todo.api.domain.Todo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    /*
     * SELECT * FROM TODOS
     *   WHERE 1=1
     *   AND writer = writer1
     * */
    List<Todo> findByWriter(String writer, Sort sort);

    /*
    * SELECT * FROM TODOS
    *   WHERE 1=1
    *   AND writer In (writer1,writer2,writer3)
    * */
    List<Todo> findAllByWriterInOrderByStartTimeAscEndTimeAscCreatedDesc(List<String> writer);
}
