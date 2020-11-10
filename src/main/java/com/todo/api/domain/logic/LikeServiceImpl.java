package com.todo.api.domain.logic;

import com.todo.api.domain.Like;
import com.todo.api.domain.Todo;
import com.todo.api.domain.service.LikeService;
import com.todo.api.exception.LikeExistException;
import com.todo.api.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    @Override
    public void addLike(Todo todo,String accountId) throws LikeExistException{
        Optional<Like> likeOtp = likeRepository.findByTodoAndAccountId(todo,accountId);
        //Optional안에 객체가 존재하는 경우
        if(likeOtp.isPresent()) throw new LikeExistException("like is exist "+accountId);

        likeRepository.save(Like.builder().todo(todo).accountId(accountId).build());
    }

    @Override
    public void removeLike(Todo todo,String accountId) throws NoSuchElementException {
        Like like = likeRepository.findByTodoAndAccountId(todo,accountId).orElseThrow(()->new NoSuchElementException(accountId));
        likeRepository.delete(like);
    }

    @Override
    public boolean checkLiked(Todo todo, String accountId) {
        Optional<Like> likeOtp = likeRepository.findByTodoAndAccountId(todo,accountId);
        //현재 좋아요를 누른 상태
        if(likeOtp.isPresent()) return true;
        //좋아요를 누르지 않은 상태
        return false;
    }


}
