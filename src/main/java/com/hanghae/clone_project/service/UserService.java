package com.hanghae.clone_project.service;

import com.hanghae.clone_project.dto.requestDto.SignupDto;
import com.hanghae.clone_project.entity.User;
import com.hanghae.clone_project.repository.UserRepository;
import com.hanghae.clone_project.validation.SignupValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final SignupValidator signupValidator;

    public void registerUser(SignupDto signupDto){

        //유효성 검사.
        signupValidator.checkUserInfoValidation(signupDto);

        //User
        User userInfo = User.of(signupDto,passwordEncoder);

        //
        userRepository.save(userInfo);
    }

}
