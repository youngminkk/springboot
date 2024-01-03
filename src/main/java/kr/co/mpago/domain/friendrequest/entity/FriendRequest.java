package kr.co.mpago.domain.friendrequest.entity;

import jakarta.persistence.*;
import kr.co.mpago.domain.BaseTimeEntity;
import kr.co.mpago.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "FriendRequests")
public class FriendRequest extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "ENUM('pending', 'accepted', 'declined') DEFAULT 'pending'")
    private Status status = Status.PENDING;

    @ManyToOne
    @JoinColumn(name = "sender_no", nullable = false, referencedColumnName="no")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "reciever_no", nullable = false, referencedColumnName="no")
    private User receiver;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;

    public FriendRequest(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public enum Status {
        PENDING,
        ACCEPTED,
        DECLINED
    }
}
