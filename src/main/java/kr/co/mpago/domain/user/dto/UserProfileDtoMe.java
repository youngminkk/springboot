package kr.co.mpago.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class UserProfileDtoMe {
    private final String nickname;
    private final String profileImg;
    @Builder
    public UserProfileDtoMe(String nickname, String introduction, String profileImg, String bgImg) {
        this.nickname = nickname;
        this.profileImg = profileImg;
    }
}
