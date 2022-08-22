package com.hanghae.clone_project.validation;

import com.hanghae.clone_project.dto.requestDto.SignupDto;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class SignupValidator {

    private static final String ID_PATTERN = "^[a-zA-Z0-9]{6,15}$";
    private static final String PASSWORD_PATTERN =
            "^((?=.*[0-9])(?=.*[!@#$%^&*()])|" +
            "(?=.*[0-9])(?=.*[a-zA-Z])|" +
            "(?=.*[a-zA-z])(?=.*[!@#$%^&*()]))" +
            "[A-Za-z0-9!@#$%^&*()]" +
            "{4,16}$";
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-z]+$";
    private static final String BIRTHDAY_PATTERN =
            "^(19[0-9][0-9]|20\\d{2})-" +
            "(0[0-9]|1[0-2])-" +
            "(0[1-9]|[1-2][0-9]|3[0-1])$";

    public void checkUserInfoValidation(SignupDto signupDto) {

        if(!Pattern.matches(ID_PATTERN,signupDto.getUsername())){
            throw new IllegalArgumentException("ID 정보가 유효하지 않습니다");
        }
        if(!Pattern.matches(PASSWORD_PATTERN,signupDto.getPassword())){
            throw new IllegalArgumentException("Password 정보가 유효하지 않습니다");
        }
        if(!Pattern.matches(EMAIL_PATTERN,signupDto.getEmail())){
            throw new IllegalArgumentException("Email 정보가 유효하지 않습니다");
        }
        if(!Pattern.matches(BIRTHDAY_PATTERN,signupDto.getBirthday())){
            throw new IllegalArgumentException("생일정보가 유효하지 않습니다");
        }
        if(!signupDto.getPassword().equals(signupDto.getPasswordConfirm())){
            throw new IllegalArgumentException("비밀번호가 서로 다릅니다.");
        }
    }

}
