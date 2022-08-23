package com.hanghae.clone_project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.clone_project.dto.requestDto.KakaoUserInfoDto;
import com.hanghae.clone_project.dto.requestDto.SignupDto;
import com.hanghae.clone_project.dto.responseDto.LoginInfoDto;
import com.hanghae.clone_project.dto.responseDto.ResponseDto;
import com.hanghae.clone_project.entity.User;
import com.hanghae.clone_project.exception.ErrorCode.CustomErrorCode;
import com.hanghae.clone_project.exception.Exception.RestApiException;
import com.hanghae.clone_project.repository.UserRepository;
import com.hanghae.clone_project.validation.SignupValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SignupValidator signupValidator;

    public ResponseEntity<?> registerUser(SignupDto signupDto) {

        //유효성 검사.
        signupValidator.checkUserInfoValidation(signupDto);

        Optional<User> found = userRepository.findByUsername(signupDto.getUsername());

        if (found.isPresent()) {
            throw new RestApiException(CustomErrorCode.DUPLICATE_RESOURCE);
        }

        User userInfo = User.of(signupDto, passwordEncoder);

        userRepository.save(userInfo);


        return new ResponseEntity<>(ResponseDto.success(LoginInfoDto.builder().username(userInfo.getUsername()).build()), HttpStatus.OK);

    }

    public void kakaoLogin(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code);

        // 2. "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        getKakaoUserInfo(accessToken);

        // 3. 필요시에 회원가입

        // 4. 강제 로그인 처리

    }

    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "b33afc37b871369aa70fb7f435d61bdb");
        body.add("redirect_uri", "http://localhost:8080/user/kakao/callback");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        System.out.println("id :"+id);
        System.out.println("nickname :"+nickname);
        System.out.println("email : "+email);

        return KakaoUserInfoDto.builder()
                .id(id)
                .nickname(nickname)
                .email(email)
                .build();


    }
}
