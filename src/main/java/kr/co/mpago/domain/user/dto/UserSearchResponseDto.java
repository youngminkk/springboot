package kr.co.mpago.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSearchResponseDto {
    private Long userNo;
    private String nickName;
    private String profileImg;


    @Builder
    public UserSearchResponseDto(Long userNo, String nickName, String userImg) {
        this.userNo = userNo;
        this.nickName = nickName;
        this.profileImg = userImg;
    }
}
