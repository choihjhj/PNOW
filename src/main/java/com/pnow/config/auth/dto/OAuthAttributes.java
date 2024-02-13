//package com.pnow.config.auth.dto;
//
//
//import com.pnow.domain.user.Role;
//import com.pnow.domain.user.User;
//import lombok.Builder;
//import lombok.Getter;
//
//import java.util.Map;
//
//@Getter
//public class OAuthAttributes {
//    private Map<String, Object> attributes;
//    private String nameAttributeKey;
//    private String name;
//    private String email;
//    private String picture;
//
//    @Builder
//    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
//        this.attributes = attributes;
//        this.nameAttributeKey = nameAttributeKey;
//        this.name = name;
//        this.email = email;
//        this.picture = picture;
//    }
//
//    // OAuth2User에서 반환하는 사용자 정보는 Map이기 때문에 값 하나하나를 변환
//
//    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
//        /*
//        if("naver".equals(registrationId)) { //naver인지 판단하는 코드
//            return ofNaver("id", attributes);
//        }
//        */
//        return ofGoogle(userNameAttributeName, attributes);
//    }
//
//    //구글 인증
//    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
//        return OAuthAttributes.builder()
//                .name( (String) attributes.get("name") )
//                .email( (String) attributes.get("email") )
//                .picture( (String) attributes.get("picture") )
//                .attributes(attributes)
//                .nameAttributeKey(userNameAttributeName)
//                .build();
//    }
//
//
//
//
//    public User toEntity() { //Role USER로 셋팅
//        return User.builder()
//                .name(name)
//                .email(email)
//                .picture(picture)
//                .role(Role.USER)
//                .build();
//    }
//}