package kr.co.mpago.domain.chatroom.entity;

import jakarta.persistence.*;
import kr.co.mpago.domain.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "ChatRooms")
public class ChatRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_no")
    private Long roomNo;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "participants", columnDefinition = "INT DEFAULT 10")
    private Integer maxParticipants = 10;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;
}
