package com.todo.api.web;

import com.todo.api.config.JwtTokenProvider;
import com.todo.api.domain.Todo;
import com.todo.api.domain.service.LikeService;
import com.todo.api.domain.service.TodoService;
import com.todo.api.web.dto.TodoAddDto;
import com.todo.api.web.dto.TodoDto;
import com.todo.api.web.dto.TodoModifyDto;
import com.todo.api.web.message.ErrorMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Api(tags = {"1. Todo"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/todos") //컨트롤러 기본 URL
public class TodoController {

    private final TodoService todoService;
    private final LikeService likeService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "TODO 리스트 조회", notes = "메인페이지에서 TODO리스트를 조회한다.")
    @GetMapping(value = "/all")
    public List<TodoDto> getTodoList(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        String accountId = jwtTokenProvider.getAccountId(token);

        List<Todo> todos = todoService.getTodos();
        return todos.stream().map(todo -> new TodoDto(todo,likeService.checkLiked(todo, accountId))).collect(Collectors.toList());
    }

    @ApiOperation(value = "나의 TODO 리스트 조회", notes = "내가 작성한 Todo와 내가 팔로우한 사람의 Todo를 조회한다.")
    @GetMapping
    public List<TodoDto> getMyTodoList(HttpServletRequest request){
        //토큰 취득
        String token = jwtTokenProvider.resolveToken(request);
        //토큰을 Decode하여 AccountId정보 취득
        String accountId = jwtTokenProvider.getAccountId(token);
        List<String> writers = new ArrayList<>();
        writers.add(accountId);

        List<Todo> todos = todoService.getMyTodos(writers);
        return todos.stream().map(todo->new TodoDto(todo,likeService.checkLiked(todo, accountId))).collect(Collectors.toList());
    }


    @ApiOperation(value = "TODO 상세 정보 조회", notes = "메인페이지에서 모달창을 열었을 때 TODO정보를 조회한다.")
    @GetMapping("/{todoId}")
    public TodoDto getTodoDetail(@PathVariable Long todoId, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        String accountId = jwtTokenProvider.getAccountId(token);

        Todo todo = todoService.getTodo(todoId);
        boolean checkLiked = likeService.checkLiked(todo,accountId);

        return new TodoDto(todo, checkLiked);
    }

    @ApiOperation(value = "TODO 생성", notes = "메인페이지에서 TODO를 생성한다.")
    @PostMapping
    public TodoAddDto addTodo(@RequestBody TodoAddDto todoAddDto) {
        todoService.addTodo(todoAddDto.toEntity());
        return todoAddDto;
    }

    @ApiOperation(value = "TODO 수정", notes = "메인페이지에서 TODO를 수정한다.")
    @PutMapping
    public TodoModifyDto modifyTodo(@RequestBody TodoModifyDto todoModifyDto) {
        Todo todo = todoModifyDto.toEntity(todoService.getTodo(todoModifyDto.getTodoId()));
        System.out.println(todoModifyDto.toString());

        todoService.modifyTodo(todoModifyDto.toEntity(todo));
        return todoModifyDto;
    }

    @ApiOperation(value = "TODO 삭제", notes = "TODO를 삭제한다.")
    @DeleteMapping("/{todoId}")
    public String deleteTodo(@PathVariable Long todoId) {
        Todo deleteTodo = todoService.getTodo(todoId);
        todoService.deleteTodo(todoId);
        return "delete success";
    }


    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody
    ErrorMessage runTimeError(RuntimeException e) throws NoSuchElementException {
        ErrorMessage error = new ErrorMessage();
        error.setMessage(e.getMessage());
        return error;
    }
}
