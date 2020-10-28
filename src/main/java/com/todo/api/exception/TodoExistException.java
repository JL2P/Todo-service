package com.todo.api.exception;

/**
 * 예외처리 커스텀
 * 해당 Todo가 존재하지 않을 때 발생 시키는 Exception
 * **/
public class TodoExistException extends RuntimeException {
    public TodoExistException(String msg, Throwable t) {
        super(msg, t);
    }
    public TodoExistException(String msg) {
        super(msg);
    }
    public TodoExistException() {
        super();
    }
}
