package kr.co.mpago.domain.user.service;

import kr.co.mpago.domain.user.dto.LoginRequestDto;
import kr.co.mpago.domain.user.entity.User;
import kr.co.mpago.domain.user.repository.UserRepository;
import kr.co.mpago.global.config.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.co.mpago.global.config.jwt.service.JwtService.logoutTokens;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserLoginStateService {

    private final UserRepository userRepository;
    private final JwtService jwtService;


    @Transactional
    public ResponseEntity<LoginRequestDto> loginState(String token) {
        log.info("userloginstateservice");
        if (token == null) {
            LoginRequestDto loginRequestDto = LoginRequestDto.builder().loginState(false).userNo(null).nickname(null).profileImg(null).build();
            return ResponseEntity.status(HttpStatus.OK).body(loginRequestDto);
        } else {
            if (logoutTokens.contains(token)) {
                LoginRequestDto loginRequestDto = LoginRequestDto.builder().loginState(false).userNo(null).nickname(null).profileImg(null).build();
                return ResponseEntity.status(HttpStatus.OK).body(loginRequestDto);
            } else {
                try {
                    String email = jwtService.extractEmail(token).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));
                    User user = userRepository.findByEmail(email)
                            .orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 없습니다."));
                    LoginRequestDto loginRequestDto = LoginRequestDto.builder().loginState(true).userNo(user.getNo()).nickname(user.getNickname()).profileImg(user.getProfileImg()).build();
                    return ResponseEntity.status(HttpStatus.OK).body(loginRequestDto);
                } catch (Exception e) {
                    LoginRequestDto loginRequestDto = LoginRequestDto.builder().loginState(false).userNo(null).nickname(null).profileImg(null).build();
                    return ResponseEntity.status(HttpStatus.OK).body(loginRequestDto);

                }
            }
        }
    }
}
