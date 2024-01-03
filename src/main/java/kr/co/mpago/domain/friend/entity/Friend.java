package kr.co.mpago.domain.friend.entity;

import jakarta.persistence.*;
import kr.co.mpago.domain.BaseTimeEntity;
import kr.co.mpago.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Friends")
public class Friend extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false, referencedColumnName = "no")
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_no", nullable = false, referencedColumnName = "no")
    private User friend;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;

    public Friend(User user, User friend) {
        this.user = user;
        this.friend = friend;
    }
}