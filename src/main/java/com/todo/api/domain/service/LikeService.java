package com.todo.api.domain.service;

import com.todo.api.domain.Todo;
import com.todo.api.exception.LikeExistException;

import java.util.NoSuchElementException;

public interface LikeService {

    public void addLike(Todo todo,String accountId)  throws LikeExistException;
    public void removeLike(Todo todo,String accountId) throws NoSuchElementException;
    public boolean checkLiked(Todo todo, String accountId);
}
