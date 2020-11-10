package com.todo.api.exception;

public class LikeExistException extends RuntimeException {
    public LikeExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public LikeExistException(String msg) {
        super(msg);
    }

    public LikeExistException() {
        super();
    }
}
