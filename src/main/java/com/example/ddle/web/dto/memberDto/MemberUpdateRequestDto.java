package com.example.ddle.web.dto.memberDto;

import com.example.ddle.domain.member.Address;
import jakarta.persistence.Embedded;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberUpdateRequestDto {

    private String nickName;
    private String password;

    @Embedded
    private Address address;

    private String phone;

    @Builder
    public MemberUpdateRequestDto(String nickName, String password, Address address, String phone) {
        this.nickName = nickName;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }
}
