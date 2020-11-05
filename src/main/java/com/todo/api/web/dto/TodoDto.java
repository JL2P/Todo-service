package com.todo.api.web.dto;

import com.todo.api.domain.Todo;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
public class TodoDto {
    private long todoId;            // Id
    private String imgUrl;      // 이미지 URL
    private String title;       // 제목
    private String description; // 설명글
    private String category;    // 카테고리
    private String writer;      // 작성자ID
    private String endTime;     // 마감일자
    private String groupAt;     // 그룹계획여부
    private int likePoint;      // 좋아요
    private List<CommentDto> comments;

    public TodoDto(Todo todo) {
        this.todoId = todo.getId();
        this.imgUrl = todo.getImgUrl();
        this.title = todo.getTitle();
        this.description = todo.getDescription();
        this.category = todo.getCategory();
        this.writer = todo.getWriter();
        this.endTime = todo.getEndTime();
        this.groupAt = todo.getGroupAt();
        this.likePoint = todo.getLikePoint();
        this.comments = todo.getComments().stream().map(comment -> new CommentDto(comment)).collect(Collectors.toList());
    }

//    public Todo toEntity() {
//        return Todo.builder()
//                .id(this.id)
//                .imgUrl(this.imgUrl)
//                .title(this.title)
//                .category(this.category)
//                .writer(this.writer)
//                .endTime(this.endTime)
//                .groupAt(this.groupAt)
//                .likePoint(this.likePoint)
//                .build();
//    }
}
