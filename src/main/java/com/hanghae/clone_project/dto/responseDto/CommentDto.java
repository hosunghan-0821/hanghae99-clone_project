package com.hanghae.clone_project.dto.responseDto;

import com.hanghae.clone_project.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CommentDto {
    private Long id;
    private String content;
    private String username;
    private String createdAt;

    public static CommentDto of(Comment comment){
        return CommentDto.builder()
                .id(comment.getId())
                .username(comment.getUser().getUsername())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt().toString())
                .build();
    }
}
