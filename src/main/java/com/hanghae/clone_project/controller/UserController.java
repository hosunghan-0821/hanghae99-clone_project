package com.hanghae.clone_project.controller;

import com.hanghae.clone_project.dto.requestDto.SignupDto;
import com.hanghae.clone_project.dto.responseDto.ResponseDto;
import com.hanghae.clone_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/v1/signup")
    public void  registerUser(@RequestBody SignupDto signupDto){
        userService.registerUser(signupDto);
    }

}
