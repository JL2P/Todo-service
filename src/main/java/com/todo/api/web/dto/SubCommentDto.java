package com.todo.api.web.dto;


import com.todo.api.domain.SubComment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SubCommentDto {
    private Long subCommentId;
    private Long commentId;   // CommentId
    private String targetId;    // 어떤 사람의 댓글인지
    private String text;        // 내용
    private String writer;      // 작성자ID
    private String likePoint;   // 좋아요

    public SubCommentDto(SubComment subComment) {
        this.commentId = subComment.getComment().getId();
        this.subCommentId = subComment.getId();
        this.text = subComment.getText();
        this.writer = subComment.getWriter();
        this.likePoint = subComment.getLikePoint();
        this.targetId = subComment.getTargetId();
    }
}
