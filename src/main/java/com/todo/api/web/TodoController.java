package com.todo.api.web;

import com.todo.api.domain.Todo;
import com.todo.api.domain.service.TodoService;
import com.todo.api.web.dto.TodoAddDto;
import com.todo.api.web.dto.TodoDto;
import com.todo.api.web.dto.TodoModifyDto;
import com.todo.api.web.message.ErrorMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Api(tags = {"1. Todo"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/todos") //컨트롤러 기본 URL
public class TodoController {

    private final TodoService todoService;

    @ApiOperation(value="TODO 리스트 조회", notes="메인페이지에서 TODO리스트를 조회한다.")
    @GetMapping
    public List<TodoDto> getTodoList(){
        List<Todo> todos= todoService.getTodos();
        return todos.stream().map(todo-> new TodoDto(todo)).collect(Collectors.toList());
    }

    @ApiOperation(value="TODO 상세 정보 조회", notes="메인페이지에서 모달창을 열었을 때 TODO정보를 조회한다.")
    @GetMapping("/{todoId}")
    public TodoDto getTodoDetail(@PathVariable Long todoId){
        return new TodoDto(todoService.getTodo(todoId));
    }

    @ApiOperation(value="TODO 생성", notes="메인페이지에서 TODO를 생성한다.")
    @PostMapping
    public TodoAddDto addTodo(@RequestBody TodoAddDto todoAddDto){
        todoService.addTodo(todoAddDto.toEntity());
        return todoAddDto;
    }

    @ApiOperation(value="TODO 수정", notes="메인페이지에서 TODO를 수정한다.")
    @PutMapping
    public TodoModifyDto modifyTodo(@RequestBody TodoModifyDto todoModifyDto){
        todoService.modifyTodo(todoModifyDto.toEntity());
        return todoModifyDto;
    }

    @ApiOperation(value="TODO 삭제", notes="TODO를 삭제한다.")
    @DeleteMapping("/{todoId}")
    public TodoDto deleteTodo(@PathVariable Long todoId){
        Todo deleteTodo = todoService.getTodo(todoId);
        todoService.deleteTodo(todoId);
        return new TodoDto(deleteTodo);
    }


    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody
    ErrorMessage runTimeError(RuntimeException e) throws NoSuchElementException {
        ErrorMessage error = new ErrorMessage();
        error.setMessage(e.getMessage());
        return error;
    }
}
