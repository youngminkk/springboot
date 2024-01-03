package kr.co.mpago.domain.user.service;

import kr.co.mpago.domain.user.entity.User;
import kr.co.mpago.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        // 사용자 생성 로직 구현
        return userRepository.save(user);
    }

    public User updateUser(Long no, String nickname, String picture) {
        // 사용자 정보 업데이트 로직 구현
        User user = userRepository.findById(no).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. no=" + no));
        user.update(nickname, picture);
        return userRepository.save(user);
    }
}
