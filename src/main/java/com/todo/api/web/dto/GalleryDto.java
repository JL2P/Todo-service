package com.todo.api.web.dto;

import com.todo.api.domain.Gallery;
import com.todo.api.domain.Todo;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GalleryDto implements Serializable {
    private Long id;
    private String title;
    private String filePath;
    private Long todoId;

    public GalleryDto(Gallery gallery){
        BeanUtils.copyProperties(gallery,this);
        this.todoId = gallery.getTodo().getId();
    }

    public Gallery toDomain(Todo todo){
        Gallery gallery = new Gallery();
        BeanUtils.copyProperties(this,gallery);

        gallery.setTodo(todo);
        return gallery;
    }

}
