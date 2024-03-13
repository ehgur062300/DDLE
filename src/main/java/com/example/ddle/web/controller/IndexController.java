package com.example.ddle.web.controller;

import com.example.ddle.domain.member.Member;
import com.example.ddle.service.MemberService;
import com.example.ddle.web.dto.exceptionDto.BasicResponse;
import com.example.ddle.web.dto.exceptionDto.CommonResponse;
import com.example.ddle.web.dto.exceptionDto.ErrorResponse;
import com.example.ddle.web.dto.loginDto.LoginRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

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
    public String login(){
        return "login";
    }

    @PostMapping("/member/login")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String login(@RequestBody LoginRequestDto requestDto) {

        // email 인증 실패 OR password 인증 실패
        if(!memberService.checkPassword(requestDto.getPassword(), requestDto.getEmail())
                || !memberService.existsByEmail(requestDto.getEmail())){
            return "login";
        }
        Member member = memberService.findByEmail(requestDto.getEmail())
                .orElseThrow(NoSuchElementException::new);

        // member model 로 넘기기

        return "index";
    }

}
