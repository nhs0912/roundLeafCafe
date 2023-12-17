package com.ypdchurch.roundleafcafe.member.controller;

import com.ypdchurch.roundleafcafe.common.dto.ResponseDTO;
import com.ypdchurch.roundleafcafe.member.service.MemberService;
import com.ypdchurch.roundleafcafe.member.service.dto.JoinRequestDto;
import com.ypdchurch.roundleafcafe.member.service.dto.JoinResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String join() {
        return "join method";
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinRequestDto joinRequestDto, BindingResult bindingResult) throws IllegalAccessException {

        log.info("heeseok join.joinRequestDto = {} ", joinRequestDto);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(new ResponseDTO<>(HttpStatus.CREATED, "유효성 검사 싫패", errorMap), HttpStatus.OK);
        }

        JoinResponseDto joinResponseDto = memberService.registerMember(joinRequestDto);
        return new ResponseEntity<>(
                new ResponseDTO<>(
                        HttpStatus.CREATED, "회원가입 성공", joinResponseDto)
                , HttpStatus.CREATED);

    }


}
