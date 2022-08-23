package com.hanghae.clone_project.service;

import com.hanghae.clone_project.dto.request.CommentRequestDto;
import com.hanghae.clone_project.dto.response.CommentResponseDto;
import com.hanghae.clone_project.entity.Comment;
import com.hanghae.clone_project.entity.Product;
import com.hanghae.clone_project.entity.User;
import com.hanghae.clone_project.repository.CommentRepository;
import com.hanghae.clone_project.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final ProductRepository productRepository;

    // 리뷰 생성
    @Transactional
    public CommentResponseDto<?> createComment(CommentRequestDto commentRequestDto, User user,Long id){
        Product product = productRepository.findById(id).orElseThrow(()->new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        Comment comment = new Comment(commentRequestDto,user,product);
        commentRepository.save(comment);
        return CommentResponseDto.success("");
    }
    //리뷰 수정
    @Transactional
    public CommentResponseDto<?> updateComment(CommentRequestDto commentRequestDto, Long id, User user){
        Comment comment = commentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        comment.update(commentRequestDto);
        return CommentResponseDto.success("");
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
        return CommentResponseDto.success("");

    }
}
