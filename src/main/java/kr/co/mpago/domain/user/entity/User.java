package kr.co.mpago.domain.user.entity;

import kr.co.mpago.domain.BaseTimeEntity;
import jakarta.persistence.*;
import kr.co.mpago.domain.user.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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

    @Column(length = 255, nullable = false)
    private String password;

    @Column(length =255, nullable = false)
    private String name;

    @Column(length =10, nullable = false, unique = true)
    private String nickname;

    @Column(length =255, nullable = false)
    private String email;

    @Column(length =255, nullable = false)
    private String picture;

    @Column(name = "admin", nullable = false)
    private boolean admin;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String password, String name, String email, String picture, boolean admin, boolean isDeleted, String nickname, Role role) {
        this.password = password;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.admin = admin;
        this.isDeleted = false;
        this.nickname = nickname;
        this.role = role;
    }
    public User update(String nickname, String picture){
        this.nickname = nickname;
        this.picture = picture;

        return this;
    }
    public String getRoleKey(){
        return this.role.getKey();
    }
}