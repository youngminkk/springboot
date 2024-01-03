package kr.co.mpago.domain.user.dto;

import kr.co.mpago.domain.user.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserSignUpDto {
    private final String password;
    private final String name;
    private final String email;
    private final String picture;
    private final boolean admin;
    private final boolean isDeleted;
    private final String nickname;
    private final Role role;

}
