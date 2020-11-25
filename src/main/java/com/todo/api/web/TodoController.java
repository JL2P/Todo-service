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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    @PostMapping("/my")
    public List<TodoDto> getMyTodoList(HttpServletRequest request, @RequestBody ArrayList<String> followers ){
        //토큰 취득
        String token = jwtTokenProvider.resolveToken(request);
        //토큰을 Decode하여 AccountId정보 취득
        String accountId = jwtTokenProvider.getAccountId(token);
        followers.add(accountId);

        List<Todo> todos = todoService.getMyTodos(followers);
        return todos.stream().map(todo->new TodoDto(todo,likeService.checkLiked(todo, accountId))).collect(Collectors.toList());
    }

    @ApiOperation(value = "로그인된 account의 todoList 조회", notes = "로그인된 account의 todoList 조회")
    @GetMapping("/login")
    public List<TodoDto> getLoginTodos(HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        String accountId = jwtTokenProvider.getAccountId(token);
        List<Todo> todos = todoService.getSelectTodos(accountId);
        return todos.stream().map(todo->new TodoDto(todo,likeService.checkLiked(todo, accountId))).collect(Collectors.toList());
    }

    @ApiOperation(value = "선택된 account의 todoList 조회", notes = "선택된 account의 todoList 조회")
    @GetMapping("/account/{selectId}")
    public List<TodoDto> getSelectTodos(@PathVariable String selectId){
        List<Todo> todos = todoService.getSelectTodos(selectId);
        return todos.stream().map(todo->new TodoDto(todo,likeService.checkLiked(todo, selectId))).collect(Collectors.toList());
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
    public TodoDto addTodo(@RequestBody TodoAddDto todoAddDto) {
        Todo todo = todoService.addTodo(todoAddDto.toEntity());
        return new TodoDto(todo,false);
    }

    @ApiOperation(value = "기간별 Todo 생성", notes = "메인페이지에서 Todo의 기간을 설정하고 설정한 기간만큼 Todo를 생성한다.")
    @PostMapping("/day")
    public List<TodoDto> addDayTodo(@RequestBody TodoAddDto todoAddDto) {
        String todoKind = todoAddDto.getTodoKind();
        String todoSubKind = todoAddDto.getTodoSubKind();
        List<Todo> addedTodos = new ArrayList<>();

        if(todoKind.equals("DAY")) {
            LocalDate start = LocalDate.parse(todoAddDto.getStartTime(), DateTimeFormatter.ISO_DATE);
            LocalDate end = LocalDate.parse(todoAddDto.getEndTime(), DateTimeFormatter.ISO_DATE);
            //기간내 Todo를 생성한다.
            while (start.isBefore(end)) {
                int day = todoService.dayCodeToDayConverter(todoSubKind);
                Todo newTodo = todoAddDto.toEntity();
                // 시작일자와 마감일자를 설정
                newTodo.setStartTime(start.toString());
                newTodo.setEndTime(start.toString());

                addedTodos.add(newTodo);
                //다음 기간설정
                start = start.plusDays(day);
            }
        }

        return todoService.addTodos(addedTodos).stream().map(addedTodo -> new TodoDto(addedTodo,false)).collect(Collectors.toList());
    }

    @ApiOperation(value = "요일별 Todo 생성", notes = "메인페이지에서 Todo의 요일을 설정하고 설정한 기간만큼 Todo를 생성한다.")
    @PostMapping("/week")
    public List<TodoDto> addWeekTodo(@RequestBody TodoAddDto todoAddDto) {
        String todoKind = todoAddDto.getTodoKind();
        String todoSubKind = todoAddDto.getTodoSubKind();
        List<Todo> addedTodos = new ArrayList<>();

        if(todoKind.equals("WEEK")) {
            LocalDate start = LocalDate.parse(todoAddDto.getStartTime(), DateTimeFormatter.ISO_DATE);
            LocalDate end = LocalDate.parse(todoAddDto.getEndTime(), DateTimeFormatter.ISO_DATE);
            while (start.isBefore(end)) {
                String week = todoService.weekToCodeConverter(start.getDayOfWeek().getValue());
                //설정한 요일과 같은 경우 Todo를 생성한다.
                if (week.equals(todoSubKind)) {
                    Todo newTodo = todoAddDto.toEntity();
                    // 시작일자와 마감일자를 설정
                    newTodo.setStartTime(start.toString());
                    newTodo.setEndTime(start.toString());
                    addedTodos.add(newTodo);
                    //일주일 뒤로 이동
                    start = start.plusDays(7);
                }else{
                    //다음날로 이동
                    start = start.plusDays(1);
                }
            }
        }

        return todoService.addTodos(addedTodos).stream().map(addedTodo -> new TodoDto(addedTodo,false)).collect(Collectors.toList());
    }


    @ApiOperation(value = "TODO 수정", notes = "메인페이지에서 TODO를 수정한다.")
    @PutMapping
    public TodoModifyDto modifyTodo(@RequestBody TodoModifyDto todoModifyDto) {
        Todo todo = todoModifyDto.toEntity(todoService.getTodo(todoModifyDto.getTodoId()));

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


    @ApiOperation(value = "TODO 완료", notes = "TODO를 완료한다.")
    @PostMapping("/{todoId}/complete")
    public String completeTodo(@PathVariable Long todoId) {
        Todo todo = todoService.getTodo(todoId);
        todo.comleted();
        todoService.modifyTodo(todo);
        return "todo complete success";
    }

    @ApiOperation(value = "TODO 미완료", notes = "TODO를 완료를 해지한다.")
    @PostMapping("/{todoId}/incomplete")
    public String incompleteTodo(@PathVariable Long todoId) {
        Todo todo = todoService.getTodo(todoId);
        todo.incompleted();
        todoService.modifyTodo(todo);
        return "todo incomplete success";
    }



    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody
    ErrorMessage runTimeError(RuntimeException e) throws NoSuchElementException {
        ErrorMessage error = new ErrorMessage();
        error.setMessage(e.getMessage());
        return error;
    }
}
