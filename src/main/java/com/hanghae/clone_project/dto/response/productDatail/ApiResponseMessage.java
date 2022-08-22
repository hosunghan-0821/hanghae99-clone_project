package com.hanghae.clone_project.dto.response.productDatail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseMessage {

    private String status;          // HttpStatus
    private String message;         // Http Default Message
    private String errorMessage;    // Error Message to USER
    private String errorCode;       // Error Code

}
