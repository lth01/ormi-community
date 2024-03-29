package com.community.controller;

import com.community.domain.dto.AddMemberRequest;
import com.community.domain.dto.MemberResponse;
import com.community.domain.dto.ModifyInfoRequest;
import com.community.domain.dto.WithdrawalRequest;
import com.community.domain.entity.Member;
import com.community.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register") //회원가입
    public ResponseEntity<MemberResponse> signup(@RequestBody AddMemberRequest request) {
        Member member = memberService.save(request);
        MemberResponse response = MemberResponse.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/modifyInfo") //회원정보 수정 (프론트 연결 시 AuthenticationPrincipal 확인)
    public ResponseEntity<Member> modify(@RequestBody ModifyInfoRequest request) {
        Member modMember = memberService.update(request);
        return ResponseEntity.ok().body(modMember);
    }

    @PutMapping("/withdrawal") //탈퇴 구현 -> 탈퇴 시 "/logout"으로 이동
    public ResponseEntity<?> withdrawal(@RequestBody WithdrawalRequest request) {
        //@AuthenticationPrincipal Member member, member.getEmail()
        if (memberService.withdrawal(request)) return ResponseEntity.ok().body("회원 탈퇴 처리 되었습니다.");
        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
    }
}
