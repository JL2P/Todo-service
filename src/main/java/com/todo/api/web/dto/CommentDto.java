package com.todo.api.web.dto;

import com.todo.api.domain.Comment;
import com.todo.api.domain.SubComment;
import com.todo.api.domain.Todo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CommentDto {

    private Long commentId;
    private Long todoId;
    private String text;        // 내용
    private String writer;      // 작성자ID
    private String likePoint;   // 좋아요
    private List<SubCommentDto> subComments = new ArrayList<SubCommentDto>();

    public CommentDto(Comment comment){
        this.commentId = comment.getId();
        this.text = comment.getText();
        this.writer = comment.getWriter();
        this.likePoint = comment.getLikePoint();
        this.todoId = comment.getTodo().getId();
        this.subComments = comment.getSubComments().stream().map(subComment-> new SubCommentDto(subComment)).collect(Collectors.toList());
    }
}
