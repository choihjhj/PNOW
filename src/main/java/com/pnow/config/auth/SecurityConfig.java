package com.pnow.config.auth;

import com.pnow.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity  // Spring Security를 활성화
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() // h2-console 화면을 위해 해당 옵션들 disable 처리
                .and()
                .authorizeHttpRequests()
                .antMatchers("/", "/css/**", "/img/**", "/js/**","/scss/**","/vendor/**", "/h2-console/**","/stores/**", "/districts/**","/profile").permitAll() //이쪽 url들은 아무런 권한없이 들어갈 수 있다.
                .antMatchers("/bookmarks/**", "/reservaions/**").hasRole(Role.USER.name()) //지정된 옵션에는 전체 열람 권한 부여 => 권한이 ROLE_USER인 경우
                .anyRequest().authenticated() //로그인 한 사용자들에게 허용
                .and()
                .logout()
                .logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 페이지
                .invalidateHttpSession(true) // HTTP 세션 파기 여부
                .clearAuthentication(true) // 인증정보 삭제 여부
                .deleteCookies("JSESSIONID") // 삭제할 쿠키 지정
                .and()
                .oauth2Login() //OAuth2 로그인 기능에 대한 여러 설정의 진입점
                .userInfoEndpoint() //OAuth2 로그인 성공 이후 사용자 정보 가져올 때의 설정 담당
                .userService(customOAuth2UserService); //소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체 등록
    }

}