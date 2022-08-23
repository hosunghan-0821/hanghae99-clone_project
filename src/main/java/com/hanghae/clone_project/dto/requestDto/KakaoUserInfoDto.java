package com.hanghae.clone_project.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class KakaoUserInfoDto {

    private Long id;
    private String nickname;
    private String email;

}
