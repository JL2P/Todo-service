package com.todo.api.domain.logic;

import com.todo.api.domain.Comment;
import com.todo.api.domain.SubComment;
import com.todo.api.domain.Todo;
import com.todo.api.domain.service.CommentService;
import com.todo.api.exception.CommentExistException;
import com.todo.api.repository.CommentRepository;
import com.todo.api.repository.SubCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final SubCommentRepository subCommentRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<Comment> getComments(Todo todo) throws NoSuchElementException {
        return commentRepository.findByTodo(todo);
    }

    @Override
    public Comment getComment(Long commentId) throws NoSuchElementException {
        //데이터가 하나도 없을 경우 빈 comment객체 반환
        if (!commentIsExist(commentId)) return new Comment();

        return commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException());
    }

    @Override
    public Comment addComment(Comment comment) throws CommentExistException {
        return commentRepository.save(comment);
    }


    @Override
    public Comment modifyComment(Comment comment) throws NoSuchElementException {
        //DB에 comment가 존재하는지 확인
        if (!commentIsExist(comment.getId())) throw new NoSuchElementException(comment.getId().toString());

        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) throws NoSuchElementException {
        if (!commentIsExist(commentId)) throw new NoSuchElementException(commentId.toString());

        commentRepository.deleteById(commentId);
    }

    @Override
    public SubComment getSubComment(Long subCommentId) throws NoSuchElementException {
        //데이터가 하나도 없을 경우 빈 comment객체 반환
        if (!subCommentIsExist(subCommentId)) return new SubComment();

        return subCommentRepository.findById(subCommentId).orElseThrow(() -> new NoSuchElementException());
    }

    @Override
    public SubComment addSubComment(SubComment subComment) throws NoSuchElementException {
       return subCommentRepository.save(subComment);
    }

    @Override
    public SubComment modifySubComment(SubComment subComment) throws NoSuchElementException {
        //DB에 comment가 존재하는지 확인
        if (!subCommentIsExist(subComment.getId())) throw new NoSuchElementException(subComment.getId().toString());

        return subCommentRepository.save(subComment);
    }

    @Override
    public void deleteSubComment(Long subCommentId) throws NoSuchElementException {
        if (!subCommentIsExist(subCommentId)) throw new NoSuchElementException(subCommentId.toString());

        subCommentRepository.deleteById(subCommentId);
    }



    public boolean commentIsExist(Long commentId) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        //Optional안에 comment객체가 존재하는 경우
        if (commentOpt.isPresent()) return true;

        //Optional안에 comment객체가 존재하지 않는 경우
        return false;
    }


    public boolean subCommentIsExist(Long subCommentId) {
        Optional<SubComment> subCommentOpt = subCommentRepository.findById(subCommentId);
        //Optional안에 comment객체가 존재하는 경우
        if (subCommentOpt.isPresent()) return true;

        //Optional안에 comment객체가 존재하지 않는 경우
        return false;
    }

}
