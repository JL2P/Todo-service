package com.todo.api.web.dto;

import com.todo.api.domain.Comment;
import com.todo.api.domain.SubComment;
import com.todo.api.domain.Todo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
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
    private String created;
    private List<SubCommentDto> subComments = new ArrayList<SubCommentDto>();

    public CommentDto(Comment comment) {
        this.commentId = comment.getId();
        this.text = comment.getText();
        this.writer = comment.getWriter();
        this.likePoint = comment.getLikePoint();
        this.todoId = comment.getTodo().getId();
        this.created = getBeforeDay(comment.getCreated());
        this.subComments = comment.getSubComments().stream().map(subComment -> new SubCommentDto(subComment)).collect(Collectors.toList());
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
