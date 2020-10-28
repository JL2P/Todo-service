package com.todo.api.domain.service;

import com.todo.api.domain.Todo;
import com.todo.api.exception.TodoExistException;

import java.util.List;
import java.util.NoSuchElementException;

public interface TodoService {
    public List<Todo> getTodos() throws NoSuchElementException;
    public Todo getTodo(Long todoId) throws NoSuchElementException;
    public void addTodo(Todo todo) throws TodoExistException;
    public Todo modifyTodo(Todo todo) throws NoSuchElementException;
    public void deleteTodo(Long todoId) throws NoSuchElementException;
    public boolean isExist(Long todoId);
}
