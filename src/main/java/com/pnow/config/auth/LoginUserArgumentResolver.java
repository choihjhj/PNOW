package com.pnow.config.auth;

import com.pnow.config.auth.dto.SessionUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import javax.servlet.http.HttpSession;
/*
* HandlerMethodArgumentResolver 인터페이스를 구현한 클래스
* (조건에 맞는 경우 구현체가 지정한 값으로 해당 메소드의 파라미터로 넘길 수 있음)
* */
@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) { //컨트롤러 메소드의 특정 파라미터를 지원하는지 판단
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null; //@LoginUser 어노테이션이 붙어 있는지
        boolean isUserClass = SessionUserDTO.class.equals(parameter.getParameterType()); //파라미터 클래스 타입이 SessionUser.class인 경우 true 반환

        return isLoginUserAnnotation && isUserClass;
    }

    @Override // 파라미터에 전달할 객체 생성 -> 세션에서 객체 가져 옴
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user");
    }
}