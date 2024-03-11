package com.example.ddle.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

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
}
