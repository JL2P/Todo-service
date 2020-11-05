package com.todo.api.exception;


/**
 * 예외처리 커스텀
 * 해당 Comment가 존재하지 않을 때 발생 시키는 Exception
 **/
public class CommentExistException extends RuntimeException {
    public CommentExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public CommentExistException(String msg) {
        super(msg);
    }

    public CommentExistException() {
        super();
    }
}