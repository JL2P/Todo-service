package com.todo.api.todo.comment;

import com.todo.api.domain.Comment;
import com.todo.api.domain.Todo;
import com.todo.api.repository.CommentRepository;
import com.todo.api.repository.TodoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
public class CommentTests {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Before
    public void setUp() {
//        String accountId = "shoon2430";
//
//        Todo newTodo = Todo.builder()
//                .category("test")
//                .title("local")
//                .description("host")
//                .endTime("test")
//                .groupAt("N")
//                .writer(accountId)
//                .build();
//
//        todoRepository.save(newTodo);
    }


    @Test
    public void 댓글을_등록한다_test() {
        List<Todo> todos = todoRepository.findAll();
        Todo todo = todos.get(0);

        Comment comment = Comment.builder()
                .todo(todo)
                .build();

        //when
        Comment newComment = commentRepository.save(comment);

        //then
    }
}
