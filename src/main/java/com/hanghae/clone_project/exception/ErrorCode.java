package com.hanghae.clone_project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

//    400 BAD_REQUEST : 잘못된 요청


//    401 UNAUTHORIZED : 인증되지 않은 사용자


//    404 NOT_FOUND : Resource를 찾을 수 없음
    NOT_FOUND_PRODUCT_ID(HttpStatus.NOT_FOUND, "404", "상품 아이디가 존재하지 않습니다.");

//    500 Sever  Error


    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;
}
