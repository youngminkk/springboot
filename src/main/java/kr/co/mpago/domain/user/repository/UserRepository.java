package kr.co.mpago.domain.user.repository;

import kr.co.mpago.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long no);
    Optional<User> findByRefreshToken(String refreshToken);

    @Query("SELECT u FROM User u WHERE LOWER(u.nickname) = :nickname")
    Optional<User> findUserBySameNickName(@Param("nickname") String nickname);
    @Query("SELECT u FROM User u WHERE LOWER(u.nickname) LIKE CONCAT('%', :nickname, '%') ORDER BY CASE WHEN u.nickname = :nickname THEN 1 ELSE 0 END DESC")
    List<User> findByNickname(@Param("nickname") String nickname);
}
