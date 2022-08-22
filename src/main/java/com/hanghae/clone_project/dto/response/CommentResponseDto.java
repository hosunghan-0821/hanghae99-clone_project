package com.hanghae.clone_project.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentResponseDto<T> {
    private boolean success;
    private T data;
    private Error error;

    public static <T> CommentResponseDto<T> success(T data){
        return new CommentResponseDto<>(true,data,null);
    }

    public static <T> CommentResponseDto<T> fail(String code, String message){
        return new CommentResponseDto<>(false,null,new Error(code,message));
    }

    @Getter
    @AllArgsConstructor
    static class Error{
        private String code;
        private String message;
    }

}
