package kr.co.mpago.domain.mountain.entity;

import jakarta.persistence.*;
import kr.co.mpago.domain.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Mountains")
public class Mountain extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 255, nullable = false)
    private String name;

    private Integer height;

    @Column(name = "manager_phone", length = 20)
    private String managerPhone;

    @Column(columnDefinition = "TEXT")
    private String details;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private Boolean isDeleted = false;
}
