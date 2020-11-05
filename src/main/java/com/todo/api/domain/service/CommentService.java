package com.todo.api.domain.service;

import com.todo.api.domain.Comment;
import com.todo.api.domain.SubComment;
import com.todo.api.domain.Todo;
import com.todo.api.exception.CommentExistException;

import java.util.List;
import java.util.NoSuchElementException;

public interface CommentService {
    public List<Comment> getComments(Todo todo) throws NoSuchElementException;

    public Comment getComment(Long commentId) throws NoSuchElementException;

    public void addComment(Comment comment) throws CommentExistException;

    public Comment modifyComment(Comment comment) throws NoSuchElementException;

    public void deleteComment(Long commentId) throws NoSuchElementException;


    public SubComment getSubComment(Long subCommentId) throws NoSuchElementException;

    public void addSubComment(SubComment subComment) throws NoSuchElementException;

    public SubComment modifySubComment(SubComment subComment) throws NoSuchElementException;

    public void deleteSubComment(Long subCommentId) throws NoSuchElementException;

    public boolean isExist(Long commentId);
}
