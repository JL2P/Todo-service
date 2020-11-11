package com.todo.api.domain.service;

import com.todo.api.domain.Todo;
import com.todo.api.exception.TodoExistException;

import java.util.List;
import java.util.NoSuchElementException;

public interface TodoService {
    public List<Todo> getTodos() throws NoSuchElementException;

    //내가 작성한 Todo와 팔로우한 사람의 Todo를 조회한다.
    public List<Todo> getMyTodos(List<String> writers) throws NoSuchElementException;

    public List<Todo> getSelectTodos(String writer) throws NoSuchElementException;

    public Todo getTodo(Long todoId) throws NoSuchElementException;

    public void addTodo(Todo todo) throws TodoExistException;

    public Todo modifyTodo(Todo todo) throws NoSuchElementException;

    public void deleteTodo(Long todoId) throws NoSuchElementException;

    public boolean isExist(Long todoId);
}
