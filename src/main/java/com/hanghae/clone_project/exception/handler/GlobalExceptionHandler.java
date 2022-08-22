package com.hanghae.clone_project.exception.handler;


import com.hanghae.clone_project.exception.ErrorCode.CommonErrorCode;
import com.hanghae.clone_project.exception.ErrorCode.Errorcode;
import com.hanghae.clone_project.exception.Exception.RestApiException;
import com.hanghae.clone_project.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleCustomException(RestApiException e){
        Errorcode errorcode = e.getErrorcode();
        return handleExceptionInternal(errorcode);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e){
        Errorcode errorcode = CommonErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorcode,e.getMessage());
    }



    private ResponseEntity<Object> handleExceptionInternal(Errorcode errorcode,String message){
        return ResponseEntity.status(errorcode.getHttpStatus()).body(makeErrorResponse(errorcode,message));
    }

    private ResponseEntity<Object> handleExceptionInternal(Errorcode errorcode){
        return ResponseEntity.status(errorcode.getHttpStatus()).body(makeErrorResponse(errorcode));
    }
    private ErrorResponse makeErrorResponse(Errorcode errorcode){

        return ErrorResponse.builder()
                .code(errorcode.name())
                .message(errorcode.getMessage())
                .build();
    }
    private ErrorResponse makeErrorResponse(Errorcode errorcode, String message){
        return ErrorResponse.builder()
                .code(errorcode.name())
                .message(message)
                .build();
    }
}
