package kr.co.mpago.domain.sociallogin.dto;

import kr.co.mpago.domain.sociallogin.entity.SocialLogins;
import kr.co.mpago.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@RequiredArgsConstructor
public class SocialLoginsDto {
    private final String providerId;
    private final SocialLogins.Provider provider;
    private final String providerAccessToken;
    private final String oauth2RefreshToken;
    private final Boolean isDeleted;
    private final User user;
}
