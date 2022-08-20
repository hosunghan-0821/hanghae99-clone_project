package com.hanghae.clone_project.dto.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDto {

    private String username;
    private String password;
    private String passwordConfirm;
    private String email;
    private String birthday;
}
