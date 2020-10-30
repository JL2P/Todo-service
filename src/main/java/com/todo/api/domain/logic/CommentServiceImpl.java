package com.todo.api.domain.logic;

import com.todo.api.domain.Comment;
import com.todo.api.domain.Todo;
import com.todo.api.domain.service.CommentService;
import com.todo.api.exception.CommentExistException;
import com.todo.api.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public List<Comment> getComments(Long todoId) throws NoSuchElementException {

        List<Comment> comments = commentRepository.findAllComments(todoId);
        return comments;
    }


    @Override
    public Comment getComment(Long commentId) throws NoSuchElementException {
        //데이터가 하나도 없을 경우 빈 group객체 반환
        if(!isExist(commentId)) return new Comment();

        return commentRepository.findById(commentId).orElseThrow(()-> new NoSuchElementException());
    }

    @Override
    public void addComment(Comment comment) throws CommentExistException {
        commentRepository.save(comment);
    }

    @Override
    public Comment modifyComment(Comment comment) throws NoSuchElementException {
        //DB에 comment가 존재하는지 확인
        if(!isExist(comment.getId()))throw new NoSuchElementException(comment.getId().toString());

        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) throws NoSuchElementException {
        if(!isExist(commentId))throw new NoSuchElementException(commentId.toString());

        commentRepository.deleteById(commentId);
    }

    @Override
    public boolean isExist(Long commentId) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        //Optional안에 comment객체가 존재하는 경우
        if(commentOpt.isPresent()) return true;

        //Optional안에 comment객체가 존재하지 않는 경우
        return false;
    }
}
