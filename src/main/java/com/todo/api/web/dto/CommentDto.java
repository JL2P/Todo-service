package com.todo.api.web.dto;

import com.todo.api.domain.Comment;
import com.todo.api.domain.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDto {
    private Long commentId;
    private long todoId;
    private String upper;       // 상위 댓글
    private String level;
    private String text;        // 내용
    private String writer;      // 작성자ID
    private String likePoint;   // 좋아요

    public CommentDto(Comment comment){
        this.commentId = comment.getId();
        this.text = comment.getText();
        this.writer = comment.getWriter();
        this.likePoint = comment.getLikePoint();
        this.upper = comment.getUpper();
        this.level = comment.getLevel();
        this.todoId = comment.getTodo().getId();
    }

    public Comment toEntity(Todo todo) {
        return Comment.builder()
                .id(this.commentId)
                .text(this.text)
                .writer(this.writer)
                .likePoint(this.likePoint)
                .upper(this.upper)
                .todo(todo)
                .build();
    }
}
