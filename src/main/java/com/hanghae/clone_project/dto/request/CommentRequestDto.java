package com.hanghae.clone_project.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDto {
    private String content;

    public CommentRequestDto(String content) {
        this.content = content;
    }

}
