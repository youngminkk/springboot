package kr.co.mpago.domain.attachment.entity;
import jakarta.persistence.*;
import kr.co.mpago.domain.BaseTimeEntity;
import kr.co.mpago.domain.chatroom.entity.ChatRoom;
import kr.co.mpago.domain.message.entity.Message;
import kr.co.mpago.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "Attachments")
public class Attachments extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(name = "file_name", length = 255, nullable = false)
    private String fileName;

    @Column(name = "file_type", length = 50, nullable = false)
    private String fileType;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "file_path", length = 255, nullable = false)
    private String filePath;

    @Column(name = "thumbnail_path", length = 255)
    private String thumbnailPath;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;

    @Column(name = "uuid", length = 255, unique = true, nullable = false)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "mno", nullable = true)
    private Message message;

    @ManyToOne
    @JoinColumn(name = "room_no", nullable = true)
    private ChatRoom chatRoom;
}
