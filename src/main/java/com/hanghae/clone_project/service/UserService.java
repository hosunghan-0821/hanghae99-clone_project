package com.hanghae.clone_project.service;

import com.hanghae.clone_project.dto.requestDto.SignupDto;
import com.hanghae.clone_project.entity.User;
import com.hanghae.clone_project.repository.UserRepository;
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


    public void registerUser(SignupDto signupDto){
        User userInfo = User.builder()
                .username(signupDto.getUsername())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .email(signupDto.getEmail())
                .birthday(signupDto.getBirthday())
                .build();

        userRepository.save(userInfo);
    }

}
