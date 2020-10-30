package com.todo.api.web;

import com.todo.api.domain.Comment;
import com.todo.api.domain.Todo;
import com.todo.api.domain.service.CommentService;
import com.todo.api.domain.service.TodoService;
import com.todo.api.web.dto.CommentAddDto;
import com.todo.api.web.dto.CommentDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"2. Comment"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/todos") //컨트롤러 기본 URL
public class CommentController {

    private final TodoService todoService;
    private final CommentService commentService;

    @GetMapping("/{todoId}/comments")
    public List<CommentDto> getComments(@PathVariable Long todoId){
        List<Comment> comments = commentService.getComments(todoId);
        comments.stream().forEach(comment -> System.out.println(comment));

       return comments.stream().map(comment -> new CommentDto(comment)).collect(Collectors.toList());
    }

    @PostMapping("/{todoId}/comments")
    public CommentAddDto addComment(@PathVariable Long todoId, @RequestBody CommentAddDto commentAddDto){
        Todo todo = todoService.getTodo(todoId);
        Comment comment = commentAddDto.toEntity(todo);
        commentService.addComment(comment);
        return commentAddDto;
    }
}
