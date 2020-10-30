package com.todo.api.todo;

import com.todo.api.domain.Todo;
import com.todo.api.repository.TodoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class TodoTests {

    @Autowired
    private TodoRepository repository;

    @Test
    public void todoAddTest() throws Exception {

        LocalDateTime now = LocalDateTime.of(2020, 10, 29, 0, 0, 0);
        repository.save(new Todo());

        //when
        List<Todo> todos = repository.findAll();

        //then
        Todo todo = todos.get(0);
        System.out.println(">>>>> createDate = "+todo.getCreated() + ", modifiedDate = "+todo.getModified());

        assertThat(todo.getCreated()).isAfter(now);
        assertThat(todo.getModified()).isAfter(now);

    }
}
