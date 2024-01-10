package kr.co.mpago.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.mpago.domain.user.dto.LoginRequestDto;
import kr.co.mpago.domain.user.dto.UserProfileDto;
import kr.co.mpago.domain.user.dto.UserProfileDtoMe;
import kr.co.mpago.domain.user.service.UserLoginStateService;
import kr.co.mpago.domain.user.service.UserProfileService;
import kr.co.mpago.domain.user.service.UserService;
import kr.co.mpago.global.config.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserProfileService userProfileService;
    private final JwtService jwtService;
    private final UserLoginStateService userLoginStateService;


    @PutMapping("/auth/profile")
    public ResponseEntity userProfileMe(@RequestBody UserProfileDtoMe userProfileDtoMe,
                                        Authentication authentication) {
        log.info("userProfileMe");
        String email = authentication.getName(); // 현재 사용자의 이메일 추출

        // 사용자 정보 업데이트 및 서비스 호출
        return userProfileService.userProfileMe(userProfileDtoMe, email);


    }

    @PutMapping("/profile")

    public ResponseEntity userProfile(@RequestBody UserProfileDto userProfileDto) {
        log.info("userProfile");
        return userProfileService.userProfile(userProfileDto);
    }

    //TODO: 만료된 토큰 처리 해야함
    //TODO: 토큰 추출 하는 부분 jwtservice 예외 핸들링에 추가적인 에러 처리 필요 여부 결정 해야함
    @GetMapping("/state")
    public ResponseEntity<LoginRequestDto> userLoginState(HttpServletRequest request) {
        Optional<String> accessToken = jwtService.extractAccessToken(request);
        if (accessToken.isPresent()) {
            return userLoginStateService.loginState(accessToken.get());
        } else {
            return userLoginStateService.loginState(null);
        }

    }
}