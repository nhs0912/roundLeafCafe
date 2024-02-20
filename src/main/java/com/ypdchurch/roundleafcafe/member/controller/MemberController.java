package com.ypdchurch.roundleafcafe.member.controller;

import com.ypdchurch.roundleafcafe.member.controller.dto.JoinRequest;
import com.ypdchurch.roundleafcafe.member.controller.dto.JoinResponse;
import com.ypdchurch.roundleafcafe.member.controller.dto.SigninRequest;
import com.ypdchurch.roundleafcafe.member.controller.dto.SigninResponse;
import com.ypdchurch.roundleafcafe.member.domain.Member;
import com.ypdchurch.roundleafcafe.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberController {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final MemberService memberService;

    @GetMapping("/join")
    public String join() {
        log.info("memberController join GET METHOD");
        return "join";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/join")
    public JoinResponse join(@RequestBody JoinRequest joinRequest) {
        log.info("heeseok join.joinRequestDto = {} ", joinRequest);
        Member registeredMember = memberService.registerMember(joinRequest.toEntity(passwordEncoder));
        return new JoinResponse(registeredMember);
    }

    @GetMapping("/signin")
    public String signin() {
        return "회원가입 page";
    }
    @PostMapping("/signin")
    public SigninResponse signin(@RequestBody SigninRequest signinRequest) {
        log.info("post signin request = {}", signinRequest);
        Long signinMemberId = memberService.findEmailAndPassword(signinRequest);
        return SigninResponse.builder()
                .id(signinMemberId)
                .build();
    }
}
