package com.example.ddle.web.controller;

import com.example.ddle.domain.SessionConst;
import com.example.ddle.domain.member.Member;
import com.example.ddle.service.MemberService;
import com.example.ddle.web.dto.loginDto.LoginRequestDto;
import com.example.ddle.web.dto.memberDto.MemberResponseDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@SessionAttributes("member")
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final MemberService memberService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/member/signUp")
    public String signUp(){
        return "signUp";
    }

    @GetMapping("/member/login")
    public String login() { return "login"; }

    @PostMapping("/member/login")
    public String login(@Valid @RequestBody LoginRequestDto requestDto, HttpSession session) {

        // 로그인 성공
        if(memberService.authenticate(requestDto)){

            Member member = memberService.findByEmail(requestDto.getEmail())
                    .orElseThrow(NoSuchElementException::new);


            // member session 에 저장
            session.setAttribute("member",new MemberResponseDto(member.getEmail(), member.getNickName()));
            return "redirect:/";
        }

        return "login";
    }


}
