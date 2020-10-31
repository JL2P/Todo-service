package com.todo.api.web.dto;

import com.todo.api.domain.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class TodoModifyDto {
    private long todoId;            // Id
    private String title;       // 제목
    private String description; // 설명글
    private String category;    // 카테고리
    private String endTime;     // 마감일자

    public TodoModifyDto(Todo todo){
        this.todoId = todo.getId();
        this.title = todo.getTitle();
        this.description = todo.getDescription();
        this.category = todo.getCategory();
        this.endTime = todo.getEndTime();
    }

    public Todo toEntity(Todo todo) {
        return Todo.builder()
                .id(this.todoId)
                .imgUrl(todo.getImgUrl())
                .title(this.title)
                .category(this.category)
                .endTime(this.endTime)
                .description(this.description)
                .groupAt(todo.getGroupAt())
                .writer(todo.getWriter())
                .likePoint(todo.getLikePoint())
                .build();
    }
}
