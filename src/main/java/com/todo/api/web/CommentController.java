package com.todo.api.web;

import com.todo.api.domain.Comment;
import com.todo.api.domain.SubComment;
import com.todo.api.domain.Todo;
import com.todo.api.domain.service.CommentService;
import com.todo.api.domain.service.TodoService;
import com.todo.api.web.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value="댓글 전체 리스트 조회", notes="해당 Todo의 전체 댓글,대댓글을 불러온다.")
    @GetMapping("/{todoId}/comments")
    public List<CommentDto> getComments(@PathVariable Long todoId){

        List<Comment> comments = commentService.getComments(todoService.getTodo(todoId));
        return comments.stream().map(comment -> new CommentDto(comment)).collect(Collectors.toList());
    }

    @ApiOperation(value="댓글 작성", notes="해당 Todo에 댓글을 작성한다.")
    @PostMapping("/{todoId}/comments")
    public CommentAddDto addComment(@PathVariable Long todoId, @RequestBody CommentAddDto commentAddDto){
        Todo todo = todoService.getTodo(todoId);
        Comment comment = commentAddDto.toEntity(todo);
        commentService.addComment(comment);
        return commentAddDto;
    }


    @ApiOperation(value="댓글 수정", notes="해당 Todo에 댓글을 수정한다.")
    @PutMapping("/{todoId}/comments")
    public CommentModifyDto modifyComment(@PathVariable Long todoId, @RequestBody CommentModifyDto commentModifyDto){
        Comment comment = commentService.getComment(commentModifyDto.getCommentId());
        commentService.modifyComment(commentModifyDto.toEntity(comment));
        return commentModifyDto;
    }

    @ApiOperation(value="댓글 삭제", notes="해당 Todo에 댓글을 삭제한다.")
    @DeleteMapping("/{todoId}/comments/{commentId}")
    public String CommentDeleteDto(@PathVariable Long todoId,@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return "Success";
    }


    @ApiOperation(value="대댓글 작성", notes="Todo의 댓글에 대댓글을 작성한다. ")
    @PostMapping("/{todoId}/comments/{commentId}/subComments")
    public SubCommentAddDto addCommentToSubComment(@PathVariable Long todoId,@PathVariable Long commentId, @RequestBody SubCommentAddDto subCommentAddDto){
        Comment comment = commentService.getComment(commentId);
        commentService.addSubComment(subCommentAddDto.toDomain(comment));
        return subCommentAddDto;
    }


    @ApiOperation(value="대댓글 수정", notes="해당 Todo에 대댓글을 수정한다.")
    @PutMapping("/{todoId}/comments/{commentId}/subComments")
    public SubCommentModifyDto modifyComment(@PathVariable Long todoId, @RequestBody SubCommentModifyDto subCommentModifyDto){

        SubComment subComment = commentService.getSubComment(subCommentModifyDto.getSubCommentId());
        commentService.modifySubComment(subCommentModifyDto.toEntity(subComment));
        return subCommentModifyDto;
    }

    @ApiOperation(value="대댓글 삭제", notes="해당 Todo에 대댓글을 삭제한다.")
    @DeleteMapping("/{todoId}/comments/{commentId}/subComments/{subCommentId}")
    public String CommentDeleteDto(@PathVariable Long todoId,@PathVariable Long commentId, @PathVariable Long subCommentId){
        commentService.deleteSubComment(subCommentId);
        return "Success";
    }
}