package kr.co.mpago.domain.hikingplan.entity;

import jakarta.persistence.*;
import kr.co.mpago.domain.BaseTimeEntity;
import kr.co.mpago.domain.mountain.entity.Mountain;
import kr.co.mpago.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "HikingPlans")
public class HikingPlan extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "reminder", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean reminder = false;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "mno", nullable = false)
    private Mountain mountain;

}