package com.pnow.config;

import com.pnow.config.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
/*
* LoginUserArgumentResolver를 스프링에서 인식할 수 있도록하는 클래스
* */
@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver);
    }

    /*
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 정적 리소스에 대한 캐시 비활성화
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(0); // 캐시 기간을 0으로 설정하여 비활성화
    }
    */
}