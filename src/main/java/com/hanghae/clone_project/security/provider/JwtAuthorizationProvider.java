package com.hanghae.clone_project.security.provider;



import com.hanghae.clone_project.entity.User;
import com.hanghae.clone_project.repository.UserRepository;
import com.hanghae.clone_project.security.UserDetailsImpl;
import com.hanghae.clone_project.security.jwt.JwtDecoder;
import com.hanghae.clone_project.security.jwt.JwtPreProcessingToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component

public class JwtAuthorizationProvider implements AuthenticationProvider {
    private final JwtDecoder jwtDecoder;

    private final UserRepository userRepository;

    @Autowired
    public JwtAuthorizationProvider(JwtDecoder jwtDecoder, UserRepository userRepository){
        this.jwtDecoder=jwtDecoder;
        this.userRepository=userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = (String) authentication.getPrincipal();
        String username = jwtDecoder.decodeUsername(token);
//         TODO: API 사용시마다 매번 User DB 조회 필요
//          -> 해결을 위해서는 UserDetailsImpl 에 User 객체를 저장하지 않도록 수정
//          ex) UserDetailsImpl 에 userId, username, role 만 저장
//            -> JWT 에 userId, username, role 정보를 암호화/복호화하여 사용
        User user = userRepository.findByUsername(username).orElseThrow(()->new AuthenticationCredentialsNotFoundException("해당 회원정보가 없습니다."));
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
