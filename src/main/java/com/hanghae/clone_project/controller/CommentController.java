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

    @PostMapping("/api/v1/review/{productId}")
    public CommentResponseDto<?> createComment(@RequestBody CommentRequestDto commentRequestDto){
        return commentService.createComment(commentRequestDto);
    }

    @PutMapping("/api/vi/review/{productId}")
    public CommentResponseDto<?> updateComment(@RequestBody CommentRequestDto commentRequestDto, @PathVariable Long productId){
        return commentService.updateComment(commentRequestDto,productId);
    }
}
