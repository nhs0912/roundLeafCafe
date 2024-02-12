package com.ypdchurch.roundleafcafe.member.controller;

import com.ypdchurch.roundleafcafe.common.dto.ResponseDTO;
import com.ypdchurch.roundleafcafe.member.controller.dto.JoinResponseDto;
import com.ypdchurch.roundleafcafe.member.domain.Member;
import com.ypdchurch.roundleafcafe.member.service.MemberService;
import com.ypdchurch.roundleafcafe.member.controller.dto.JoinRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/join")
    public ResponseEntity<JoinResponseDto> join(@RequestBody JoinRequestDto joinRequestDto) {

        log.info("heeseok join.joinRequestDto = {} ", joinRequestDto);
        Member registeredMember = memberService.registerMember(joinRequestDto.toEntity(passwordEncoder));
        JoinResponseDto joinResponseDto = new JoinResponseDto(registeredMember);
        return new ResponseEntity<JoinResponseDto>(HttpStatus.CREATED);
    }
}
