package com.todo.api.web.dto;

import com.todo.api.domain.Comment;
import com.todo.api.domain.SubComment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubCommentModifyDto {

    private Long subCommentId;
    private String text;        // 내용

    public SubComment toEntity(SubComment subComment) {
        return SubComment.builder()
                .id(this.subCommentId)
                .text(this.text)
                .writer(subComment.getWriter())
                .likePoint(subComment.getLikePoint())
                .targetId(subComment.getTargetId())
                .comment(subComment.getComment())
                .build();
    }
}