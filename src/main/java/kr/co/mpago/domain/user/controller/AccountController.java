package kr.co.mpago.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.mpago.domain.user.dto.SignUpRequestDto;
import kr.co.mpago.domain.user.service.UserService;
import kr.co.mpago.global.config.jwt.service.JwtService;
import kr.co.mpago.global.exception.ErrorCode;
import kr.co.mpago.global.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequestDto signUpRequestDto) {
        log.info("회원가입");
        return userService.join(signUpRequestDto);
    }
    @GetMapping("/custom-logout")
    public ResponseEntity logout(HttpServletRequest request) {
        log.info("로그아웃");
        Optional<String> accessToken = jwtService.extractAccessToken(request);
        if(accessToken.isEmpty()){
            throw new UnauthorizedException(ErrorCode.INVALID_ACCESS_TOKEN_VALUE);
        }else{
            log.info("엑세스 토큰: {}" ,accessToken.get());
            return userService.logout(accessToken.get());
        }

    }
}
