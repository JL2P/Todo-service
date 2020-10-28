package com.todo.api.web.dto;

import com.todo.api.domain.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoModifyDto {
    private long id;            // Id
    private String title;       // 제목
    private String description; // 설명글
    private String category;    // 카테고리
    private String endTime;     // 마감일자

    public TodoModifyDto(Todo todo){
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.description = todo.getDescription();
        this.category = todo.getCategory();
        this.endTime = todo.getEndTime();
    }

    public Todo toEntity() {
        return Todo.builder()
                .id(this.id)
                .title(this.title)
                .category(this.category)
                .endTime(this.endTime)
                .build();
    }
}
