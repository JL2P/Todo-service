package com.todo.api.web.dto;


import com.todo.api.domain.SubComment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class SubCommentDto {
    private Long subCommentId;
    private Long commentId;   // CommentId
    private String targetId;    // 어떤 사람의 댓글인지
    private String text;        // 내용
    private String writer;      // 작성자ID
    private String likePoint;   // 좋아요
    private String created;

    public SubCommentDto(SubComment subComment) {
        this.commentId = subComment.getComment().getId();
        this.subCommentId = subComment.getId();
        this.text = subComment.getText();
        this.writer = subComment.getWriter();
        this.likePoint = subComment.getLikePoint();
        this.targetId = subComment.getTargetId();
        this.created = getBeforeDay(subComment.getCreated());
    }

    private String getBeforeDay(LocalDateTime date) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        Duration duration = Duration.between(date, currentDateTime);
        long second = duration.getSeconds();
        if (60 > second) return second + " 초 전";
        if (60 * 60 > second) return second / 60 + " 분 전";
        if (60 * 60 * 24 > second) return second / (60 * 60) + " 시간 전";
        if (60 * 60 * 24 * 30 > second) return second / (60 * 60 * 24) + " 일 전";
        if (60 * 60 * 24 * 365> second) return second / (60 * 60 * 24 * 30) + " 달 전";

        else return second / (60 * 60 * 24 * 365) + " 년 전";
    }

}
