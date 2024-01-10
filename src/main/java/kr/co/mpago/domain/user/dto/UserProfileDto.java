package kr.co.mpago.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class UserProfileDto {
    private final String nickname;
    private final String email;

    @Builder
    public UserProfileDto(String email,String nickname, String introduction) {
        this.email=email;
        this.nickname = nickname;
    }
}
