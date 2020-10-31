package com.todo.api.web.dto;

import com.todo.api.domain.Comment;
import com.todo.api.domain.SubComment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SubCommentAddDto {
    private String targetId;
    private String text;        // 내용
    private String writer;      // 작성자ID

    public SubComment toDomain(Comment comment){
        SubComment subComment = SubComment.builder()
                .comment(comment)
                .targetId(this.targetId)
                .text(this.text)
                .writer(this.writer)
                .build();
        return subComment;
    }
}
