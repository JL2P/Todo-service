package com.todo.api.domain.logic;

import com.todo.api.domain.Gallery;
import com.todo.api.domain.Todo;
import com.todo.api.domain.service.GalleryService;
import com.todo.api.repository.GalleryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class GalleryServiceImpl implements GalleryService {
    private final GalleryRepository galleryRepository;

    @Override
    public Gallery getTodoGallery(Todo todo) {
        // todo로 검색했을때, 있으면 검색된 데이터 보내주고, 없으면 null
        return galleryRepository.findByTodo(todo).orElse(null);
    }

    @Override
    public Gallery savePost(Gallery gallery) {
        return galleryRepository.save(gallery);
    }
}
