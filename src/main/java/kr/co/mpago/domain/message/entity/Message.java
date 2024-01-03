package kr.co.mpago.domain.message.entity;

import jakarta.persistence.*;
import kr.co.mpago.domain.BaseTimeEntity;
import kr.co.mpago.domain.chatroom.entity.ChatRoom;
import kr.co.mpago.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Messages")
public class Message extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATETIME", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date datetime;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName="no", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "room_no", nullable = false)
    private ChatRoom chatRoom;
}
