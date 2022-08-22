package com.hanghae.clone_project.controller;


import com.hanghae.clone_project.dto.request.CommentRequestDto;
import com.hanghae.clone_project.dto.response.CommentResponseDto;
import com.hanghae.clone_project.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    //리뷰생성
    @PostMapping("/api/v1/review/{productId}")
    public CommentResponseDto<?> createComment(@RequestBody CommentRequestDto commentRequestDto){
        return commentService.createComment(commentRequestDto);
    }

    //리뷰수정
    @PutMapping("/api/vi/review/{productId}")
    public CommentResponseDto<?> updateComment(@RequestBody CommentRequestDto commentRequestDto, @PathVariable Long id){
        return commentService.updateComment(commentRequestDto,id);
    }
    //리뷰삭제
    @DeleteMapping("/api/v1/review/{productId}")
    public CommentResponseDto<?> deleteComment(@PathVariable Long id){
        return commentService.deleteComment(id);
    }
}
