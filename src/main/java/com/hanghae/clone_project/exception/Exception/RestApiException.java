package com.hanghae.clone_project.exception.Exception;


import com.hanghae.clone_project.exception.ErrorCode.Errorcode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends  RuntimeException {
    private final Errorcode errorcode;
    private String message;


    public RestApiException(Errorcode errorcode, String message){
        this.errorcode=errorcode;
        this.message=message;
    }
}
