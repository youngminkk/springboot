package kr.co.mpago.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.mpago.domain.user.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 요청 헤더에서 JWT를 가져옵니다.
        String token = request.getHeader("Authorization");

        // 2. JWT가 있는 경우, JWT를 검증하고 사용자를 인증합니다.
//        if (token != null && tokenService.isValidToken(token)) {
//            User user = tokenService.getUserFromToken(token);
//            request.setAttribute("user", user);
//        }

        return true;
    }
}