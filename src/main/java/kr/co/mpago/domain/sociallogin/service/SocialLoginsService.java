package kr.co.mpago.domain.sociallogin.service;

import kr.co.mpago.domain.sociallogin.dto.SocialLoginsDto;
import kr.co.mpago.domain.sociallogin.entity.SocialLogins;
import kr.co.mpago.domain.sociallogin.repository.SocialLoginsRepository;
import kr.co.mpago.domain.user.entity.User;
import kr.co.mpago.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SocialLoginsService {
    private final UserRepository userRepository;
    private final SocialLoginsRepository socialLoginsRepository;

    public SocialLoginsService(UserRepository userRepository, SocialLoginsRepository socialLoginsRepository) {
        this.userRepository = userRepository;
        this.socialLoginsRepository = socialLoginsRepository;
    }

    public User registerOrUpdateSocialUser(String provider, String providerId, String accessToken, String refreshToken) {
        SocialLogins.Provider providerEnum = SocialLogins.Provider.fromValue(provider);
        Optional<SocialLogins> socialLoginOptional = socialLoginsRepository.findByProviderAndProviderId(providerEnum, providerId);
        SocialLogins socialLogin;

        if (!socialLoginOptional.isPresent()) {
            // 처음 로그인하는 경우: User 및 SocialLogins 엔티티 생성
            User user = new User();
            userRepository.save(user);

            socialLogin = new SocialLogins();
            socialLogin.setProvider(providerEnum);
            socialLogin.setProviderId(providerId);
            socialLogin.setUser(user);
        } else {
            // 이미 로그인한 적 있는 경우: SocialLogins 엔티티 갱신
            socialLogin = socialLoginOptional.get();
            socialLogin.setProviderAccessToken(accessToken);
            socialLogin.setOauth2RefreshToken(refreshToken);
        }
        socialLogin = socialLoginsRepository.save(socialLogin);
        return socialLogin.getUser();
    }

    public SocialLoginsDto login(SocialLoginsDto socialLoginsDto) {
        // 소셜 로그인 제공자와 제공자 ID를 통해 SocialLogins 엔티티를 조회
        SocialLogins socialLogins = socialLoginsRepository.findByProviderAndProviderId(socialLoginsDto.getProvider(), socialLoginsDto.getProviderId())
                .orElseThrow(() -> new IllegalArgumentException("소셜 로그인 정보를 찾을 수 없습니다."));

        // SocialLogins 엔티티에서 User 엔티티를 가져옴
        User user = socialLogins.getUser();

        // SocialLoginsDto 객체에 로그인 결과 정보 설정
        return SocialLoginsDto.builder()
                .user(user)
                .provider(socialLogins.getProvider())
                .providerId(socialLogins.getProviderId())
                .providerAccessToken(socialLogins.getProviderAccessToken())
                .oauth2RefreshToken(socialLogins.getOauth2RefreshToken())
                .build();
    }
}