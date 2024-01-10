package kr.co.mpago.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSearchRequestDto {
    private String searchNickName;


    public UserSearchRequestDto(String searchNickName){
        this.searchNickName =searchNickName;
    }
}
