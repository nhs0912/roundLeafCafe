package com.ypdchurch.roundleafcafe.member.controller;

import com.ypdchurch.roundleafcafe.common.auth.jwt.JwtProvider;
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
    private final JwtProvider jwtProvider;

    @GetMapping("/join")
    public String join() {
        log.info("memberController join GET METHOD");
        return "join";
    }

    @GetMapping("/tset")
    public String test() {
        log.info("test GET METHOD");
        throw new IllegalArgumentException("그냥 해보았따! ");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/join")
    public JoinResponse join(@RequestBody JoinRequest joinRequest) {
        log.info("heeseok join.joinRequestDto = {} ", joinRequest);
        Member registeredMember = memberService.registerMember(joinRequest.toEntity(passwordEncoder));
        return new JoinResponse(registeredMember);
    }

    @PostMapping("/signin2")
    @ResponseStatus(HttpStatus.OK)
    public SigninResponse signin(@RequestBody SigninRequest signinRequest) {
        log.info("post signin method start");
        log.info("post signin request = {}", signinRequest);
        Long signinMemberId = memberService.findEmailAndPassword(signinRequest);

        Member member = memberService.findById(signinMemberId);
        String accessToken = jwtProvider.createAccessToken(member);
        String refreshToken = jwtProvider.createRefreshToken(member, accessToken);
        log.info("accessToken = {}", accessToken);
        log.info("refreshToken = {}", refreshToken);

        SigninResponse signinResponse = SigninResponse.builder()
                .id(signinMemberId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        log.info("signinREsponse = {} ", signinResponse);

        return signinResponse;
    }
}
