//package com.pnow.config.auth;
//
//import com.pnow.domain.user.Role;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//
//@RequiredArgsConstructor
//@EnableWebSecurity  // Spring Security를 활성화
//public class SecurityConfig  extends WebSecurityConfigurerAdapter {
//
//    private final CustomOAuth2UserService customOAuth2UserService;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .headers().frameOptions().disable() // h2-console 화면을 위해 해당 옵션들 disable 처리
//                .and()
//                .authorizeHttpRequests()
//                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
//                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
//                .anyRequest().authenticated()
//                .and()
//                .logout().logoutSuccessUrl("/")
//                .and()
//                .oauth2Login()
//                .userInfoEndpoint()
//                .userService(customOAuth2UserService);
//    }
//
//}