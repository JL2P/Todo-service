package com.todo.api.domain.logic;

import com.todo.api.domain.Todo;
import com.todo.api.domain.service.TodoService;
import com.todo.api.exception.TodoExistException;
import com.todo.api.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public List<Todo> getTodos() throws NoSuchElementException {
        return todoRepository.findAll(sortByCreatedAsc());
    }

    @Override
    public List<Todo> getMyTodos(List<String> writers) throws NoSuchElementException {
        return todoRepository.findAllByWriterIn(writers, sortByCreatedAsc());
    }

    @Override
    public List<Todo> getSelectTodos(String writer) throws NoSuchElementException{
        return todoRepository.findByWriter(writer, sortByCreatedAsc());
    }


    private Sort sortByCreatedAsc() {
        return Sort.by(Sort.Direction.DESC, "created");
    }

    @Override
    public Todo getTodo(Long todoId) throws NoSuchElementException {
        //데이터가 하나도 없을 경우 빈 group객체 반환
        if (!isExist(todoId)) return new Todo();

        return todoRepository.findById(todoId).orElseThrow(() -> new NoSuchElementException());
    }

    @Override
    public Todo addTodo(Todo todo) throws TodoExistException {
        return todoRepository.save(todo);
    }

    @Override
    public List<Todo> addTodos(List<Todo> todos) {
        return todoRepository.saveAll(todos);
    }

    //요일별 코드를 구한다.
    @Override
    public int dayCodeToDayConverter(String dayCode){
        int day = 1;
        switch (dayCode){
            case "DAY1": day=1; break;
            case "DAY2": day=2; break;
            case "DAY3": day=3; break;
            case "DAY4": day=4; break;
            case "DAY5": day=5; break;
            default: day=1;
        }
        return day;
    }

    //요일별 코드를 구한다.
    @Override
    public String weekToCodeConverter(int week){
        String code = "";
        switch (week){
            case 1: code="MON"; break;
            case 2: code="TUE"; break;
            case 3: code="WEN"; break;
            case 4: code="THU"; break;
            case 5: code="FRI"; break;
            case 6: code="SAT"; break;
            case 7: code="SUN"; break;
            default: code="";
        }
        return code;
    }

    @Override
    public Todo modifyTodo(Todo todo) throws NoSuchElementException {
        //DB에 todo가 존재하는지 확인
        if (!isExist(todo.getId())) throw new NoSuchElementException(todo.getId().toString());

        return todoRepository.save(todo);
    }

    @Override
    public void deleteTodo(Long todoId) throws NoSuchElementException {
        if (!isExist(todoId)) throw new NoSuchElementException(todoId.toString());

        todoRepository.deleteById(todoId);
    }

    @Override
    public boolean isExist(Long todoId) {
        Optional<Todo> todoOpt = todoRepository.findById(todoId);
        //Optional안에 todo객체가 존재하는 경우
        if (todoOpt.isPresent()) return true;

        //Optional안에 todo객체가 존재하지 않는 경우
        return false;
    }
}
