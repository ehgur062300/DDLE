package com.example.ddle.service;

import com.example.ddle.domain.member.Member;
import com.example.ddle.domain.member.MemberRepository;
import com.example.ddle.web.dto.exceptionDto.BasicResponse;
import com.example.ddle.web.dto.exceptionDto.CommonResponse;
import com.example.ddle.web.dto.exceptionDto.ErrorResponse;
import com.example.ddle.web.dto.joinDto.SignupRequestDto;
import com.example.ddle.web.dto.loginDto.LoginRequestDto;
import com.example.ddle.web.dto.memberDto.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // Spring Security를 사용한 로그인 구현 시 사용
    // private final BCryptPasswordEncoder encoder;

    /** email 중복 체크 */
    public boolean existsByEmail(String email){
        return memberRepository.existsByEmail(email);
    }

    /** nickname, 중복 체크 */
    public boolean checkNickNameDuplicate(String nickName){
        return memberRepository.existsByNickName(nickName);
    }

    /** password 일치 여부 */
    public boolean checkPassword(String password, String email) {

        String savedPW = memberRepository.findByEmail(email)
                .orElseThrow(NoSuchElementException::new)
                .getPassword();

        return password.equals(savedPW);
    }

    public ResponseEntity<? extends BasicResponse> signUp(SignupRequestDto requestDto){

        if(existsByEmail(requestDto.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("이미 존재하는 Email입니다.","409"));
        }

        memberRepository.save(requestDto.toEntity());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse<SignupRequestDto>(requestDto));
    }

    /** 로그인 기능 */
    public Member login(LoginRequestDto requestDto){
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(NoSuchElementException::new);

        return member;
    }

    /** 이메일로 회원 찾기 */
    public Optional<Member> findByEmail(String email){
        return memberRepository.findByEmail(email);

    }

    /** 회원 정보 수정 */
    public Member update(MemberUpdateRequestDto requestDto, Integer member_id){

        Member member = memberRepository.findById(member_id)
                .orElseThrow(NoSuchElementException::new);

        return member.update(requestDto.getPassword(), requestDto.getNickName(), requestDto.getAddress(), requestDto.getPhone());
    }

    /** 회원 정보 삭제 */
    public boolean delete(Integer member_id) {
        Optional<Member> findMember = memberRepository.findById(member_id);
        if (!findMember.isPresent()) {
            return false;
        } else {
            memberRepository.deleteById(member_id);
            return true;
        }
    }

    /** 입력 양식 확인 */
    public String chkError(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());

            return String.join("| ", errors);
        }
        return "";
    }
}
