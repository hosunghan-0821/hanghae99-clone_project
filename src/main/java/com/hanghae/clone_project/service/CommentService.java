package com.hanghae.clone_project.service;

import com.hanghae.clone_project.dto.request.CommentRequestDto;
import com.hanghae.clone_project.dto.response.CommentResponseDto;
import com.hanghae.clone_project.entity.Comment;
import com.hanghae.clone_project.entity.User;
import com.hanghae.clone_project.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    // 리뷰 생성
    @Transactional
    public CommentResponseDto<?> createComment(CommentRequestDto commentRequestDto, User user){
        Comment comment = new Comment(commentRequestDto,user);
        commentRepository.save(comment);
        return CommentResponseDto.success(comment);
    }
    //리뷰 수정
    @Transactional
    public CommentResponseDto<?> updateComment(CommentRequestDto commentRequestDto, Long id, User user){
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if(optionalComment.isEmpty()){
            return CommentResponseDto.fail("NULL_POST_ID","post id isn't exist");
        }
        Comment comment = optionalComment.get();
        comment.update(commentRequestDto,user);
        return CommentResponseDto.success(comment);
    }

    //리뷰 삭제
    @Transactional
    public CommentResponseDto<?> deleteComment(Long id){
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isEmpty()) {
            return CommentResponseDto.fail("NOT_FOUND", "post id is not exist");
        }
        Comment comment = optionalComment.get();
        commentRepository.delete(comment);
        return CommentResponseDto.success(true);

    }
}
