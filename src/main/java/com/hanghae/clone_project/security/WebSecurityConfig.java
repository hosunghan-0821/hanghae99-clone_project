package com.hanghae.clone_project.security;



import com.hanghae.clone_project.security.filter.JwtAuthorizationFilter;
import com.hanghae.clone_project.security.filter.JwtAuthenticationFilter;
import com.hanghae.clone_project.security.handler.AccessDeniedHandler;
import com.hanghae.clone_project.security.handler.AuthenticationFailHandler;
import com.hanghae.clone_project.security.handler.FormLoginFailureHandler;
import com.hanghae.clone_project.security.handler.FormLoginSuccessHandler;
import com.hanghae.clone_project.security.jwt.HeaderTokenExtractor;
import com.hanghae.clone_project.security.provider.JwtAuthenticationProvider;
import com.hanghae.clone_project.security.provider.JwtAuthorizationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig {

    private final JwtAuthorizationProvider jwtAuthorizationProvider;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final HeaderTokenExtractor headerTokenExtractor;
    private final FormLoginSuccessHandler formLoginSuccessHandler;


    private final FormLoginFailureHandler formLoginFailureHandler;

    private final AccessDeniedHandler accessDeniedHandler;

    private final AuthenticationFailHandler authenticationFailHandler;



    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .antMatchers( //Swagger 문서 읽기위한 요청허용
                        "/swagger-ui/**",
                        "/v2/api-docs",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/swagger/**");
    }

    @Bean
    public SecurityFilterChain filterChain( HttpSecurity http, AuthenticationManagerBuilder auth) throws Exception {
        //인증 (Authentication)**: 사용자 신원을 확인하는 행위
        //인가 (Authorization)**: 사용자 권한을 확인하는 행위
        auth
                .authenticationProvider(jwtAuthorizationProvider)
                .authenticationProvider(jwtAuthenticationProvider());

        http.csrf().disable();
        http.cors().configurationSource(corsConfigurationSource());

        http
                .addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeRequests()
                .anyRequest()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //사용하는 필터 만들기
    @Bean
    public JwtAuthenticationFilter formLoginFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(authenticationConfiguration));
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
        jwtAuthenticationFilter.setAuthenticationFailureHandler(formLoginFailureHandler);
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(formLoginSuccessHandler);
        jwtAuthenticationFilter.afterPropertiesSet();

        return jwtAuthenticationFilter;
    }

    //사용하는 필터 만들기
    private JwtAuthorizationFilter jwtFilter() throws Exception {

        List<String> skipPathList = new ArrayList<>();

        // 회원 관리 API SKIP 적용
        skipPathList.add("POST,/api/v1/signup");

        // Post 게시글 관련
        skipPathList.add("GET,/api/v1/posts/**");

        //기본 페이지 설정
        skipPathList.add("GET,/");
        skipPathList.add("GET,/favicon.ico");

        FilterSkipMatcher matcher = new FilterSkipMatcher(skipPathList, "/**");
        JwtAuthorizationFilter filter = new JwtAuthorizationFilter(headerTokenExtractor, matcher);

        filter.setAuthenticationFailureHandler(authenticationFailHandler);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(encodePassword());
    }


    //cors 허용 적용
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("https://dev-job-liard.vercel.app");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }
}
