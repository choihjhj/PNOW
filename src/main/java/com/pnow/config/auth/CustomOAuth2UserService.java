package com.pnow.config.auth;

import com.pnow.config.auth.dto.CustomUserPrincipal;
import com.pnow.config.auth.dto.OAuthAttributesDTO;
import com.pnow.domain.user.User;
import com.pnow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.Collections;
/**
 * 소셜 로그인 이후 가져온 사용자의 정보들을 기반으로 가입 및 정보수정, 세션 저장 등의 기능 지원하는 클래스
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //인터페이스 생성
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest); //인터페이스에 userRequest를 받아 로드

        //1. 현재 로그인 진행 중인 서비스(구글, 로그인)를 구분
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        //2. OAuth2 로그인 진행 시 키가 되는 필드 = Primary Key
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        //-------- OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
        OAuthAttributesDTO attributes = OAuthAttributesDTO.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes); //소셜로그인 인증한 OAuthAttributes dto를 User 엔티티에 저장

        return new CustomUserPrincipal(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPicture(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes()
        );

    }
    private User saveOrUpdate(OAuthAttributesDTO attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .orElseGet(() -> attributes.toEntity()); // 존재하지 않는다면 새로운 사용자로 등록

        return userRepository.save(user);
    }

}