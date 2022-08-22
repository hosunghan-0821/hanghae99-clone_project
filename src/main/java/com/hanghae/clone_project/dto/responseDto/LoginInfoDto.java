package com.hanghae.clone_project.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginInfoDto {
    private String username;




    public static LoginInfoDto of(String username){
        return LoginInfoDto.builder()
                .username(username)
                .build();
    }
}
