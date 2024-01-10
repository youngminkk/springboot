package kr.co.mpago.domain.user.entity;

import kr.co.mpago.domain.BaseTimeEntity;
import jakarta.persistence.*;
import kr.co.mpago.domain.user.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE no = ?")
@Where(clause = "is_deleted = false")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(length =255)
    private String email;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(length =10, nullable = false, unique = true)
    private String nickname;

    private String profileImg;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String refreshToken;

    @Builder
    public User(String email, String password, String profileImg, String nickname, Role role, String refreshToken, boolean isDeleted) {
        this.email = email;
        this.password = password;
        this.profileImg = profileImg;
        this.nickname = nickname;
        this.role = role;
        this.refreshToken = refreshToken;
        this.isDeleted = false;
    }
    public User update(String nickname, String profileImg){
        this.nickname = nickname;
        this.profileImg = profileImg;
        return this;
    }
    public User update (String password){
        this.password = password;
        return this;
    }
    // 비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
    public String getRoleKey(){
        return this.role.getKey();
    }
}
