package kr.co.mpago.domain.user.service;

import kr.co.mpago.domain.user.dto.UserSearchResponseDto;
import kr.co.mpago.domain.user.entity.User;
import kr.co.mpago.domain.user.repository.UserRepository;
import kr.co.mpago.global.config.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserSearchService {

    private final UserRepository userRepository;
    private String nowUserEmail = null;
    private final JwtService jwtService;


    @Transactional
    public List<UserSearchResponseDto> getResponse(String searchNickName) {
        List<User> findByNickName = userRepository.findByNickname(searchNickName);
        if(findByNickName.isEmpty()){
            log.error("유저가 없습니다.");
            return Collections.emptyList();
        }
        try {
            List<UserSearchResponseDto> userSearchResponseDtos = madeResponseDto(findByNickName);
            return userSearchResponseDtos;

        }catch (Exception e){
            throw new RuntimeException("오류가 발생했습니다.");
        }

    }

    private List<UserSearchResponseDto> madeResponseDto(List<User> findByNickName) {
        List<UserSearchResponseDto> userSearchResponseDtos = new ArrayList<>();

        for(User u : findByNickName){
            UserSearchResponseDto userSearchResponseDto = UserSearchResponseDto.builder()
                    .userNo(u.getNo())
                    .nickName(u.getNickname())
                    .userImg(u.getProfileImg())
                    .build();

            userSearchResponseDtos.add(userSearchResponseDto);
        }
        return userSearchResponseDtos;
    }

    private Boolean profileState(String nowUserEmail, Long userId) {
        Optional<User> nowUser = userRepository.findByEmail(nowUserEmail);
        Optional<User> checkUser = userRepository.findById(userId);
        if (nowUser.isPresent()) {
            return nowUser.equals(checkUser);
        } else return false;
    }
}