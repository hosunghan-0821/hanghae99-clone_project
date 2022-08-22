package com.hanghae.clone_project.service;

import com.hanghae.clone_project.dto.request.CommentRequestDto;
import com.hanghae.clone_project.dto.response.CommentResponseDto;
import com.hanghae.clone_project.entity.Comment;
import com.hanghae.clone_project.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDto<?> createComment(CommentRequestDto commentRequestDto){
        Comment comment = new Comment(commentRequestDto);
        commentRepository.save(comment);
        return CommentResponseDto.success(comment);
    }
    @Transactional
    public CommentResponseDto<?> updateComment(CommentRequestDto commentRequestDto,Long productId){
        Optional<Comment> optionalComment = commentRepository.findById(productId);
        if(optionalComment.isEmpty()){
            return CommentResponseDto.fail("NULL_POST_ID","post id isn't exist");
        }
        Comment comment = optionalComment.get();
        comment.update(commentRequestDto);
        return CommentResponseDto.success(comment);
    }
}
