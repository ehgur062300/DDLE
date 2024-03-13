package com.example.ddle.web.controller;

import com.example.ddle.domain.member.Member;
import com.example.ddle.service.MemberService;
import com.example.ddle.web.dto.exceptionDto.BasicResponse;
import com.example.ddle.web.dto.exceptionDto.CommonResponse;
import com.example.ddle.web.dto.exceptionDto.ErrorResponse;
import com.example.ddle.web.dto.joinDto.SignupRequestDto;
import com.example.ddle.web.dto.loginDto.LoginRequestDto;
import com.example.ddle.web.dto.memberDto.MemberUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;

    /** create **/
    @PostMapping("/member/save")
    public ResponseEntity<? extends BasicResponse> save(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult){

        String errorMessage = memberService.chkError(bindingResult);
        if(!errorMessage.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(errorMessage,"400"));
        }

        return memberService.signUp(requestDto);
    }


    /** read **/
    @GetMapping("/member/{email}")
    public ResponseEntity<? extends BasicResponse> findByEmail(@PathVariable("email") String email){

        Optional<Member> member = memberService.findByEmail(email);
        if(member.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("일치하는 회원 정보가 없습니다. 사용자 Email을 확인해주세요."));
        }

        return ResponseEntity.ok().body(new CommonResponse<Integer>(member.get().getId()));
    }


    /** update **/
    @PatchMapping("/member/update/{id}")
    public ResponseEntity<? extends BasicResponse> patch(@RequestBody MemberUpdateRequestDto requestDto,
                                                         @PathVariable("id") Integer member_id) {
        Member member = memberService.update(requestDto, member_id);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("일치하는 회원 정보가 없습니다. 사용자 id를 확인해주세요."));
        }
        return ResponseEntity.noContent().build();
    }


    /** delete **/
    @DeleteMapping("/{id}")
    public ResponseEntity<? extends BasicResponse> delete(@PathVariable("id") Integer member_id) {
        if (!memberService.delete(member_id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("일치하는 회원 정보가 없습니다. 사용자 id를 확인해주세요"));
        }
        return ResponseEntity.noContent().build();
    }
}
