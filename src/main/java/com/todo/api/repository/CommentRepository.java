package com.todo.api.repository;

import com.todo.api.domain.Comment;
import com.todo.api.domain.Todo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByTodo(Todo todo, Sort sort);

    @Query(value = "SELECT\n" +
                    "UPPER,\n" +
                    "ID\n" +
                    "FROM COMMENTS\n" +
                    "WHERE UPPER IS NULL;\n" +
                    "\n" +
                    "\n" +
                    "WITH RECURSIVE CTE AS (\n" +
                    "    SELECT\n" +
                    "    ID,\n" +
                    "    created,\n" +
                    "    modified,\n" +
                    "    like_point,\n" +
                    "    text,\n" +
                    "    upper,\n" +
                    "    writer,\n" +
                    "    todo_id,\n" +
                    "    1 AS 1ev,\n" +
                    "    convert(id, char(100)) as ctg_path\n" +
                    "    FROM COMMENTS\n" +
                    "    WHERE UPPER IS NULL\n" +
                    "    AND todo_id =:todoId\n"+
                    "    UNION ALL\n" +
                    "    SELECT\n" +
                    "    a.ID,\n" +
                    "    a.created,\n" +
                    "    a.modified,\n" +
                    "    a.like_point,\n" +
                    "    a.text,\n" +
                    "    a.upper,\n" +
                    "    a.writer,\n" +
                    "    a.todo_id,\n" +
                    "    1ev + 1 AS 1ev,\n" +
                    "    concat(b.ctg_path, '-', a.id) as ctg_path\n" +
                    "    FROM COMMENTS a\n" +
                    "    INNER JOIN CTE b ON a.UPPER = b.ID\n" +
                    ")\n" +
                    "SELECT\n" +
                    "    todo_id,\n" +
                    "    ID,\n" +
                    "    ifnull( UPPER, 0) AS upper,\n" +
                    "    1ev as level,\n" +
                    "    ctg_path,\n" +
                    "    created,\n" +
                    "    modified,\n" +
                    "    like_point,\n" +
                    "    text,\n" +
                    "    writer\n" +
                    "FROM CTE\n" +
                    "order by level\n" +
                    ";",
            countQuery = "SELECT COUNT(*) FROM comments",
            nativeQuery = true)
        List<Comment> findAllComments(@Param("todoId") Long todoId);

}
