package com.todo.api.web;


import com.todo.api.config.JwtTokenProvider;
import com.todo.api.domain.Todo;
import com.todo.api.domain.service.LikeService;
import com.todo.api.domain.service.TodoService;
import com.todo.api.web.message.ErrorMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Api(tags = {"3. Like"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/todos") //컨트롤러 기본 URL
public class LikeController {
    private final TodoService todoService;
    private final LikeService likeService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "좋아요 추가")
    @PostMapping("/{todoId}/like")
    public String onLike(@PathVariable Long todoId, HttpServletRequest request){
        //토큰 취득
        String token = jwtTokenProvider.resolveToken(request);
        //토큰을 Decode하여 AccountId정보 취득
        String accountId = jwtTokenProvider.getAccountId(token);
        Todo todo = todoService.getTodo(todoId);

        likeService.addLike(todo,accountId);
        return "like add success";
    }

    @ApiOperation(value = "좋아요 삭제")
    @DeleteMapping("/{todoId}/like")
    public String cancelLike(@PathVariable Long todoId, HttpServletRequest request){
        //토큰 취득
        String token = jwtTokenProvider.resolveToken(request);
        //토큰을 Decode하여 AccountId정보 취득
        String accountId = jwtTokenProvider.getAccountId(token);
        Todo todo = todoService.getTodo(todoId);

        likeService.removeLike(todo,accountId);
        return "like cancel success";
    }

    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody
    ErrorMessage runTimeError(RuntimeException e) {
        ErrorMessage error = new ErrorMessage();
        error.setMessage(e.getMessage());
        return error;
    }
}
