package com.hanghae.clone_project.exception.ErrorCode;


import org.springframework.http.HttpStatus;

public interface Errorcode {

    String name();
    HttpStatus getHttpStatus();
    String getMessage();
}
