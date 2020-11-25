package com.todo.api.domain.service;

import com.todo.api.domain.Gallery;
import com.todo.api.domain.Todo;

public interface GalleryService {

    //Todo에 맞는 Gallery를 가져온다.
    public Gallery getTodoGallery(Todo todo);
    public Gallery savePost(Gallery gallery);
}
