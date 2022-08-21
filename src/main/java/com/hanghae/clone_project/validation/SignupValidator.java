package com.hanghae.clone_project.validation;

import com.hanghae.clone_project.dto.requestDto.SignupDto;
import com.hanghae.clone_project.entity.User;
import org.springframework.stereotype.Component;

@Component
public class SignupValidator {

    public void checkUserInfoValidation(SignupDto signupDto) {
        String idPattern = "^[a-zA-Z0-9]{6,15}$";
        String passwordPattern =
                        "^((?=.*[0-9])(?=.*[!@#$%^&*()])|" +
                        "(?=.*[0-9])(?=.*[a-zA-Z])|" +
                        "(?=.*[a-zA-z])(?=.*[!@#$%^&*()]))" +
                        "[A-Za-z0-9!@#$%^&*()]" +
                        "{4,16}$";
        String emailPattern = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-z]+$";
        String birthdayPattern =
                "^(19[0-9][0-9]|20\\d{2})-" +
                "(0[0-9]|1[0-2])-" +
                "(0[1-9]|[1-2][0-9]|3[0-1])$";
    }

}
