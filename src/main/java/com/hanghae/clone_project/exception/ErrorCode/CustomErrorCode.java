package com.hanghae.clone_project.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CustomErrorCode implements  Errorcode{
    UNAUTHORIZED_TOKEN(HttpStatus.UNAUTHORIZED,"권한이 없습니다"),
    DUPLICATE_RESOURCE(HttpStatus.BAD_REQUEST,"중복된 아이디 입니다.")
    ;
    private final HttpStatus httpStatus;
    private final String message;



}
