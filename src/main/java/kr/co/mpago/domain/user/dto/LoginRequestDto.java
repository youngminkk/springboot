package kr.co.mpago.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    private Boolean loginState;
    private Long userNo;
    private String nickname;
    private String profileImg;

    @Builder
    public LoginRequestDto(Boolean loginState, Long userNo, String nickname, String profileImg) {
        this.loginState = loginState;
        this.userNo = userNo;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }
}
