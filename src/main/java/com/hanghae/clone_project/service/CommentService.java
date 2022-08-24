package com.hanghae.clone_project.service;

import com.hanghae.clone_project.dto.request.CommentRequestDto;
import com.hanghae.clone_project.dto.response.CommentResponseDto;
import com.hanghae.clone_project.dto.responseDto.CommentDto;
import com.hanghae.clone_project.dto.responseDto.ResponseDto;
import com.hanghae.clone_project.entity.Comment;
import com.hanghae.clone_project.entity.Product;
import com.hanghae.clone_project.entity.User;
import com.hanghae.clone_project.exception.ErrorCode.CustomErrorCode;
import com.hanghae.clone_project.exception.Exception.RestApiException;
import com.hanghae.clone_project.repository.CommentRepository;
import com.hanghae.clone_project.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final ProductRepository productRepository;

    // 리뷰 생성
    @Transactional
    public ResponseEntity<?> createComment(CommentRequestDto commentRequestDto, User user, Long id) {

        Product product = productRepository
                .findById(id).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        Comment comment = new Comment(commentRequestDto, user, product);
        commentRepository.save(comment);

        return new ResponseEntity<>(ResponseDto.success(CommentDto.of(comment)), HttpStatus.OK);
    }

    //리뷰 수정
    @Transactional
    public ResponseEntity<?> updateComment(CommentRequestDto commentRequestDto, Long id, User user) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if(!user.getUsername().equals(comment.getUser().getUsername()))
            throw new RestApiException(CustomErrorCode.UNAUTHORIZED_TOKEN,"수정권한이 없습니다.");

        comment.update(commentRequestDto);
        return new ResponseEntity<>(ResponseDto.success(CommentDto.of(comment)), HttpStatus.OK);
    }

    //리뷰 삭제
    @Transactional
    public ResponseEntity<?> deleteComment(Long id, User user) {

        Comment comment = commentRepository
                .findById(id).orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if(!user.getUsername().equals(comment.getUser().getUsername()))
            throw new RestApiException(CustomErrorCode.UNAUTHORIZED_TOKEN,"수정권한이 없습니다.");

        commentRepository.delete(comment);

        return new ResponseEntity<>(ResponseDto.success(null), HttpStatus.OK);

    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getComments(Long productId) {
        List<Comment> commentList = commentRepository.findAllByProduct_IdOrderByCreatedAtDesc(productId);


        List<CommentDto> commentDtoList =
                commentList.stream()
                        .map(CommentDto::of)
                        .collect(Collectors.toList());

        return new ResponseEntity<>(ResponseDto.success(commentDtoList), HttpStatus.OK);

    }
}
