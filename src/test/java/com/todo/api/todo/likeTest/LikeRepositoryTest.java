package com.todo.api.todo.likeTest;

import com.todo.api.domain.Like;
import com.todo.api.domain.Todo;
import com.todo.api.repository.LikeRepository;
import com.todo.api.repository.TodoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
public class LikeRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Before
    public void setUp() {
        String accountId = "shoon2430";

        Todo newTodo = Todo.builder()
                .id(100l)
                .category("test")
                .title("local")
                .description("host")
                .endTime("test")
                .groupAt("N")
                .writer(accountId)
                .build();
        todoRepository.save(newTodo);

    }

    @Test
    public void 이미_좋아요를_눌렀는지_확인힌다(){
        String accountId = "shoon2430";

        Todo todo = todoRepository.findAll().get(0);
        Like newLike = Like.builder().todo(todo).accountId(accountId).build();
        likeRepository.save(newLike);

        Like like = likeRepository.findByTodoAndAccountId(todo,accountId).get();

        assertThat(like.getAccountId()).isEqualTo(accountId);

    }



}
