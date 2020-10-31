package com.todo.api.web.dto;

import com.todo.api.domain.Comment;
import com.todo.api.domain.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CommentModifyDto {

    private Long commentId;
    private String text;        // 내용

    public Comment toEntity(Comment comment){
        return Comment.builder()
                .id(this.commentId)
                .text(this.text)
                .writer(comment.getWriter())
                .likePoint(comment.getLikePoint())
                .todo(comment.getTodo())
                .subComments(comment.getSubComments())
                .build();
    }
}
