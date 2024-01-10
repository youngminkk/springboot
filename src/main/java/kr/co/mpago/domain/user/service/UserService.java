package kr.co.mpago.domain.user.service;

import kr.co.mpago.domain.user.Role;
import kr.co.mpago.domain.user.dto.SignUpRequestDto;
import kr.co.mpago.domain.user.entity.User;
import kr.co.mpago.domain.user.repository.UserRepository;
import kr.co.mpago.global.config.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public ResponseEntity<?> join(SignUpRequestDto signUpRequestDto) {
        log.info("회원가입");
        String result = validateDuplicateUser(signUpRequestDto);
        log.info(result);
        if (StringUtils.hasText(result)) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            //wrapper 타입만 null 이 들어갈 수 있음
        } else {
            User user = User.builder()
                    .email(signUpRequestDto.getEmail())
                    .password(signUpRequestDto.getPassword())
                    .role(Role.GUEST)
                    .build();


            user.passwordEncode(passwordEncoder);

            userRepository.save(user);
            log.info(user.getEmail() + " 회원가입 성공");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

    }


    private String validateDuplicateUser(SignUpRequestDto signUpRequestDto) {
        Optional<User> findEmail = userRepository.findByEmail(signUpRequestDto.getEmail());

        String errorMessage = "";
        if (findEmail.isPresent()) {
            errorMessage += "이미 가입된 이메일입니다.";
        }
        return errorMessage;

    }


    public ResponseEntity logout(String accessToken) {

        log.info("엑세스 토큰: {}", accessToken);
        jwtService.logout(accessToken);
        return ResponseEntity.ok("로그아웃 완료");

    }
    @Transactional
    public ResponseEntity<String> updatePassword(String email, String updatedPassword){
        Optional<User> findByEmail = userRepository.findByEmail(email);

        if(findByEmail.isEmpty()){
            return new ResponseEntity<>(HttpStatusCode.valueOf(401));
        }
        updatePassword(updatedPassword, findByEmail);

        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    private void updatePassword(String updatedPassword, Optional<User> findByEmail) {
        User user = findByEmail.get();
        user.update(updatedPassword).passwordEncode(passwordEncoder);
        userRepository.save(user);
    }
}
