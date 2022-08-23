package com.hanghae.clone_project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae.clone_project.dto.responseDto.ResponseDto;
import com.hanghae.clone_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class KakaoUserController {

    private  final UserService userService;
    public static final String KAKAO_AUTH_URL=
            "https://kauth.kakao.com/oauth/authorize?client_id=b33afc37b871369aa70fb7f435d61bdb&" +
            "redirect_uri=http://localhost:8080/user/kakao/callback&response_type=code";

    @GetMapping("/api/v1/kakao/signup")
    public String requestToKakao(){
        return  "redirect:"+KAKAO_AUTH_URL;
    }

    @GetMapping("/user/kakao/callback")
    @ResponseBody
    public ResponseDto<?> kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        System.out.println("code : "+ code);
        userService.kakaoLogin(code);
        return ResponseDto.success("성공");
    }

}
