package com.todo.api.domain.logic;

import com.todo.api.domain.Todo;
import com.todo.api.domain.service.TodoService;
import com.todo.api.exception.TodoExistException;
import com.todo.api.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public List<Todo> getTodos() throws NoSuchElementException {
        return todoRepository.findAll(sortByCreatedAsc());
    }

    @Override
    public List<Todo> getMyTodos(List<String> writers) throws NoSuchElementException {
        System.out.println(writers.size());
        return todoRepository.findAllByWriterIn(writers, sortByCreatedAsc());
    }

    @Override
    public List<Todo> getSelectTodos(String writer) throws NoSuchElementException{
        return todoRepository.findByWriter(writer, sortByCreatedAsc());
    }


    private Sort sortByCreatedAsc() {
        return Sort.by(Sort.Direction.DESC, "created");
    }

    @Override
    public Todo getTodo(Long todoId) throws NoSuchElementException {
        //데이터가 하나도 없을 경우 빈 group객체 반환
        if (!isExist(todoId)) return new Todo();

        return todoRepository.findById(todoId).orElseThrow(() -> new NoSuchElementException());
    }

    @Override
    public Todo addTodo(Todo todo) throws TodoExistException {
        return todoRepository.save(todo);
    }

    @Override
    public Todo modifyTodo(Todo todo) throws NoSuchElementException {
        //DB에 todo가 존재하는지 확인
        if (!isExist(todo.getId())) throw new NoSuchElementException(todo.getId().toString());

        return todoRepository.save(todo);
    }

    @Override
    public void deleteTodo(Long todoId) throws NoSuchElementException {
        if (!isExist(todoId)) throw new NoSuchElementException(todoId.toString());

        todoRepository.deleteById(todoId);
    }

    @Override
    public boolean isExist(Long todoId) {
        Optional<Todo> todoOpt = todoRepository.findById(todoId);
        //Optional안에 todo객체가 존재하는 경우
        if (todoOpt.isPresent()) return true;

        //Optional안에 todo객체가 존재하지 않는 경우
        return false;
    }
}
