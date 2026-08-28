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
        
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        
        // userRequest에는 OAuth2 로그인 과정에서 획득한 Access Token이 들어있다.
        // DefaultOAuth2UserService가 Access Token을 사용하여
        // Provider의 UserInfo Endpoint에서 사용자 정보를 가져온다.
        OAuth2User oAuth2User = delegate.loadUser(userRequest); 

        // 현재 로그인에 사용한 OAuth2 Provider 확인
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        
        
        // Provider에서 사용자 식별에 사용하는 attribute 이름
        // Google의 경우 일반적으로 "sub"
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        //-------- OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
        OAuthAttributesDTO attributes = OAuthAttributesDTO.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        //소셜로그인 인증한 OAuthAttributes dto를 아래 saveOrUpdate()메서드에서 이메일 기준으로 DB에서 기존회원조회 또는 신규회원생성 후 User 엔티티로 반환
        User user = saveOrUpdate(attributes); 
        
        // 이후 Spring Security가 Authentication의 principal로 사용할
        // 애플리케이션 전용 사용자 객체 반환(OAuth2User 타입)
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