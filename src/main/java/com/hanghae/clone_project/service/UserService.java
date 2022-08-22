package com.hanghae.clone_project.service;

import com.hanghae.clone_project.dto.requestDto.SignupDto;
import com.hanghae.clone_project.entity.User;
import com.hanghae.clone_project.exception.ErrorCode.CustomErrorCode;
import com.hanghae.clone_project.exception.Exception.RestApiException;
import com.hanghae.clone_project.repository.UserRepository;
import com.hanghae.clone_project.validation.SignupValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;
import java.util.Optional;

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

        Optional<User> found=userRepository.findByUsername(signupDto.getUsername());

        if(found.isPresent()){
            throw new RestApiException(CustomErrorCode.DUPLICATE_RESOURCE);
        }

        User userInfo = User.of(signupDto,passwordEncoder);

        userRepository.save(userInfo);
    }

}
