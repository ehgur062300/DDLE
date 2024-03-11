package com.example.ddle.service;

import com.example.ddle.domain.member.Member;
import com.example.ddle.domain.member.MemberRepository;
import com.example.ddle.web.dto.joinDto.SignupRequestDto;
import com.example.ddle.web.dto.loginDto.LoginRequestDto;
import com.example.ddle.web.dto.memberDto.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // Spring Security를 사용한 로그인 구현 시 사용
//    private final BCryptPasswordEncoder encoder;

    /**
     * email 중복 체크
     */
    public boolean checkEmailDuplicate(String email){
        return memberRepository.existsByEmail(email);
    }

    /**
     * nickname, 중복 체크
     */
    public boolean checkNickNameDuplicate(String nickName){
        return memberRepository.existsByNickName(nickName);
    }

    public Member signUp(SignupRequestDto requestDto){

        Member member = Member.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .nickName(requestDto.getNickName())
                .build();

        return memberRepository.save(member);
    }

    /**
     *  로그인 기능
     */
    public Member login(LoginRequestDto requestDto){
        Optional<Member> optionalMember = memberRepository.findByEmail(requestDto.getEmail());

        if (optionalMember.isEmpty()) {
            return null;
        }

        Member member = optionalMember.get();
        if (!member.getPassword().equals(requestDto.getPassword())) {
            return null;
        }

        return member;
    }

    /**
     *  이메일로 회원 찾기
     */
    public Optional<Member> findByEmail(String email){
        return memberRepository.findByEmail(email);

    }

    /**
     * 회원 정보 수정
     */
    public Member update(MemberUpdateRequestDto requestDto, Integer member_id){

        Member member = memberRepository.findById(member_id).get();
        return member.update(requestDto.getPassword(), requestDto.getNickName(), requestDto.getAddress(), requestDto.getPhone());
    }

    /**
     * 회원 정보 삭제
     */
    public boolean delete(Integer member_id) {
        Optional<Member> findMember = memberRepository.findById(member_id);
        if (!findMember.isPresent()) {
            return false;
        } else {
            memberRepository.deleteById(member_id);
            return true;
        }
    }
}
