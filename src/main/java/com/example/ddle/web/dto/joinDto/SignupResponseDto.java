package com.example.ddle.web.dto.joinDto;

import com.example.ddle.domain.member.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SignupResponseDto {

    private final String email;
    private final String password;
    private final String nickName;

    public SignupResponseDto(Member member){
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.nickName = member.getNickName();
    }
}
