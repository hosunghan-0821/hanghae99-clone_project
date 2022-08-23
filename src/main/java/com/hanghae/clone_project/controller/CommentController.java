package com.hanghae.clone_project.controller;


import com.hanghae.clone_project.dto.request.CommentRequestDto;
import com.hanghae.clone_project.dto.response.CommentResponseDto;
import com.hanghae.clone_project.entity.User;
import com.hanghae.clone_project.security.UserDetailsImpl;
import com.hanghae.clone_project.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    //리뷰생성
    @PostMapping("/api/v1/review/{productId}")
    public CommentResponseDto<?> createComment(@RequestBody CommentRequestDto commentRequestDto,
                                               @PathVariable Long productId,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createComment(commentRequestDto,userDetails.getUser(),productId);
    }

    //리뷰수정
    @PutMapping("/api/v1/review/{commentId}")
    public CommentResponseDto<?> updateComment(@RequestBody CommentRequestDto commentRequestDto,
                                               @PathVariable Long commentId,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println("rororo");
        return commentService.updateComment(commentRequestDto,commentId,userDetails.getUser());
    }
    //리뷰삭제
    @DeleteMapping("/api/v1/review/{commentId}")
    public CommentResponseDto<?> deleteComment(@PathVariable Long commentId){
        return commentService.deleteComment(commentId);
    }
}
