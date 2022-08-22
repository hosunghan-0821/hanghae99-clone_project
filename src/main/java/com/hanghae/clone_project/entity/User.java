package com.hanghae.clone_project.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae.clone_project.dto.requestDto.SignupDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Getter
@Entity
@Table(name="users")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String birthday;

    public static User of(SignupDto signupDto, BCryptPasswordEncoder passwordEncoder){
       return User.builder()
               .username(signupDto.getUsername())
               .password(passwordEncoder.encode(signupDto.getPassword()))
               .email(signupDto.getEmail())
               .birthday(signupDto.getBirthday())
               .build();
    }
}
