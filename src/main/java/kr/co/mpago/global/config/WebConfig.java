package kr.co.mpago.global.config;

import kr.co.mpago.global.interceptor.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    private final JwtInterceptor jwtInterceptor;

    public WebConfig(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;

    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // JWT 인터셉터를 모든 요청에 적용합니다.
        registry.addInterceptor(jwtInterceptor);
    }
}
