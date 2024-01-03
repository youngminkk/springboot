package kr.co.mpago.domain.messagemanager.entity;

import jakarta.persistence.*;
import kr.co.mpago.domain.BaseTimeEntity;
import kr.co.mpago.domain.message.entity.Message;
import kr.co.mpago.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "MessageManager")
public class MessageManager extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(name = "is_read", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isRead = false;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "mno", nullable = false)
    private Message message;
}
