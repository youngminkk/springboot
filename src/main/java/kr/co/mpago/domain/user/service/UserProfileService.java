package kr.co.mpago.domain.user.service;

import kr.co.mpago.domain.user.dto.UserProfileDto;
import kr.co.mpago.domain.user.dto.UserProfileDtoMe;
import kr.co.mpago.domain.user.entity.User;
import kr.co.mpago.domain.user.repository.UserRepository;
import kr.co.mpago.global.exception.ErrorCode;
import kr.co.mpago.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserProfileService {

    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity userProfileMe(UserProfileDtoMe userProfileDtoMe, String email) {
        try{
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

            Optional<User> findUserByNickName = userRepository.findUserBySameNickName(userProfileDtoMe.getNickname());

            // 닉네임 중복검사
            // 기존 설정된 자신의 닉네임인 경우 에러 안냄
            return checkDuplicateAndSendResponse(userProfileDtoMe, user, findUserByNickName);

        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    private ResponseEntity<?> checkDuplicateAndSendResponse(UserProfileDtoMe userProfileDtoMe, User user, Optional<User> findUserByNickName) {
        if (findUserByNickName.isEmpty()) {
            log.info("닉넴 존재안함");
            user.update(userProfileDtoMe.getNickname(), userProfileDtoMe.getProfileImg());
            userRepository.saveAndFlush(user);
            return ResponseEntity.ok(null);
        }else{
            return handleNickNameError(user, findUserByNickName.get(),userProfileDtoMe);
        }
    }

    private ResponseEntity<?> handleNickNameError(User user, User findUserByNickName,UserProfileDtoMe userProfileDtoMe) {

        if (Objects.equals(user.getNo(), findUserByNickName.getNo())) {
            user.update(userProfileDtoMe.getProfileImg());
            userRepository.saveAndFlush(user);
            return ResponseEntity.status(HttpStatus.OK).body(ErrorCode.SAME_NICKNAME_CONFLICT);
        }
        log.info("중복되는 닉네임");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorCode.NICKNAME_CONFLICT);
    }

    @Transactional
    public ResponseEntity userProfile(UserProfileDto userProfileDto) {
        log.info("update");
        try {
            String email = userProfileDto.getEmail();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

            //TODO: 기존에 DB에 변경하려는 닉네임이 있는 경우 중복 알려주기
            Optional<User> findUserByNickName = userRepository.findUserBySameNickName(userProfileDto.getNickname());

            return checkDuplicateAndSendResponse(userProfileDto, user, findUserByNickName);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }


    }
    private ResponseEntity<?> checkDuplicateAndSendResponse(UserProfileDto userProfileDto, User user, Optional<User> findUserByNickName) {
        if (findUserByNickName.isEmpty()) {
            log.info("닉넴 존재안함");
            user.update(userProfileDto.getNickname());
            userRepository.saveAndFlush(user);
            return ResponseEntity.ok(null);
        }else{
            return handleNickNameError(user, findUserByNickName.get());
        }
    }
    private ResponseEntity<?> handleNickNameError(User user, User findUserByNickName) {
        if (Objects.equals(user.getNo(), findUserByNickName.getNo())) {
            return ResponseEntity.status(HttpStatus.OK).body(ErrorCode.SAME_NICKNAME_CONFLICT);

        }
        log.info("중복되는 닉네임");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorCode.NICKNAME_CONFLICT);

    }

}