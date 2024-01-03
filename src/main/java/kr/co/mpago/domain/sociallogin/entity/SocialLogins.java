package kr.co.mpago.domain.sociallogin.entity;

import kr.co.mpago.domain.BaseTimeEntity;
import kr.co.mpago.domain.user.entity.User;
import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SocialLogins")
public class SocialLogins extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(length = 255, nullable = false)
    private String providerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider;

    @Column(length = 1024, nullable = true)
    private String providerAccessToken;

    @Column(length = 1024, nullable = true)
    private String oauth2RefreshToken;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private Boolean isDeleted=false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    public enum Provider {
        KAKAO("kakao"),
        NAVER("naver"),
        GOOGLE("google");

        private String value;

        Provider(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Provider fromValue(String value) {
            return Arrays.stream(Provider.values())
                    .filter(provider -> provider.getValue().equals(value))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Invalid provider value: " + value));
        }
    }
}
