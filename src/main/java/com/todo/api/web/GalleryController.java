package com.todo.api.web;

import com.todo.api.domain.Gallery;
import com.todo.api.domain.Todo;
import com.todo.api.domain.service.GalleryService;
import com.todo.api.domain.service.S3Service;
import com.todo.api.domain.service.TodoService;
import com.todo.api.web.dto.GalleryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = {"4.Grallery "})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/todos") //컨트롤러 기본 URL
public class GalleryController {
    private final S3Service s3Service;
    private final GalleryService galleryService;
    private final TodoService todoService;

    @ApiOperation(value = "Todo S3이미지 업로드", notes = "filePath 데이터를 받아온다.")
    @PostMapping("/{todoId}/gallery")
    public GalleryDto execWrite(@RequestParam("file") MultipartFile file, @PathVariable Long todoId) throws IOException {
        Todo todo = todoService.getTodo(todoId);
        //그룹의 겔러리데이터가 있는지 있으면 가져오고 없으면 null
        Gallery gallery =galleryService.getTodoGallery(todo);

        String imgPath = s3Service.upload(file);
        String filename= file.getOriginalFilename();
        //생성자와 같지만 더 명확하게 보여줌 //데이터를 받아오는 건 엔티티 객체로 저장
        Gallery newGallery = Gallery.builder()
                .title(filename)
                .filePath(imgPath)
                .todo(todo)
                .build();

        // 겔러리가 있다는거는 UPDATE
        // 겔러리의 ID를 취득하여 newGallery에 넣어줌
        // save시에 Id값이 있으면 업데이트가됨
        if(gallery != null) newGallery.setId(gallery.getId());

        //엔티티를 DTO로 변환해서 클라이언트에 반환
        return new GalleryDto(galleryService.savePost(newGallery));
    }
}