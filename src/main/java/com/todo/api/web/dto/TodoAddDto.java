package com.todo.api.web.dto;

import com.todo.api.domain.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoAddDto  {
    private String title;       // 제목
    private String imgUrl;      // 이미지 URL
    private String description; // 설명글
    private String category;    // 카테고리
    private String writer;      // 작성자ID
    private String endTime;     // 마감일자
    private String groupAt;     // 그룹계획여부

    public TodoAddDto(Todo todo){
        this.title = todo.getTitle();
        this.imgUrl = todo.getImgUrl();
        this.description = todo.getDescription();
        this.category = todo.getCategory();
        this.writer = todo.getWriter();
        this.endTime = todo.getEndTime();
        this.groupAt = todo.getGroupAt();
    }

    public Todo toEntity() {
        return Todo.builder()
                .title(this.title)
                .imgUrl(this.imgUrl)
                .description(this.description)
                .category(this.category)
                .writer(this.writer)
                .endTime(this.endTime)
                .groupAt(this.groupAt)
                .build();
    }
}