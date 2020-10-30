package com.todo.api.web.dto;

import com.todo.api.domain.Comment;
import com.todo.api.domain.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentAddDto {
    private String text;        // 제목
    private String writer;      // 작성자ID

    public CommentAddDto(Comment comment){
        this.text = comment.getText();
        this.writer = comment.getWriter();
    }

    public Comment toEntity(Todo todo) {
        return Comment.builder()
                .text(this.text)
                .writer(this.writer)
                .todo(todo)
                .build();
    }
}