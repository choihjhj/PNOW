package com.pnow.aop;

import com.pnow.config.auth.dto.SessionUserDTO;
import com.pnow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class UserAuthorityAspect {
    private final UserRepository userRepository;

    @Before("@annotation(com.pnow.aop.CheckUserAuthority) && args(id,..)")
    public void checkUserAuthority(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //인증 정보가 없거나 principal이 null이면 로그인 필요
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new AuthenticationCredentialsNotFoundException("로그인이 필요합니다.");
        }

        SessionUserDTO user = null; // 최종 비교할 사용자

        //principal이 SessionUserDTO이면 바로 사용
        if (authentication.getPrincipal() instanceof SessionUserDTO) {
            user = (SessionUserDTO) authentication.getPrincipal();
        }
        //principal이 DefaultOAuth2User이면 이메일로 User 조회
        else if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

            //이메일을 기반으로 DB에서 User 조회
            String email = oAuth2User.getAttribute("email");
            user = new SessionUserDTO(
                    userRepository.findByEmail(email)
                            .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("유저 정보가 존재하지 않습니다."))
            );
        } else {
            throw new AuthenticationCredentialsNotFoundException("로그인이 필요합니다.");
        }


        /*-- 최종적으로 세션 로그인 id와 REST API URL에 있는 id 비교 --*/
        if (!id.equals(user.getId())) {
            log.warn("권한 없는 사용자 접근 id={}, userId={}", id, user.getId());
            throw new AccessDeniedException("권한이 없습니다.");
        }
    }


}
