package com.example.ddle.web.dto.joinDto;

import com.example.ddle.domain.member.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignupRequestDto {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}",
            message = "올바르지 않은 이메일 형식입니다.")
    private String email;


    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,16}",
            message = "비밀번호는 영문과 숫자 조합으로 8 ~ 16자리까지 가능합니다.")
    private String password;


    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Pattern(regexp = "[a-zA-Z0-9ㄱ-ㅎ가-힣]{2,9}",
            message = "닉네임은 한글, 영문, 숫자만 가능하며 2 ~ 10자리까지 가능합니다.")
    private String nickName;


    @Builder
    public SignupRequestDto(String email, String password, String nickName) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
    }

    public Member toEntity(){
        return Member.builder()
                .email(this.email)
                .password(this.password)
                .nickName(this.nickName)
                .build();
    }
}
