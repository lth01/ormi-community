package com.community.controller;

import com.community.domain.dto.*;
import com.community.domain.entity.Member;
import com.community.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
        MemberResponse response = memberService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/modifyInfo") //회원정보 수정 (프론트 연결 시 AuthenticationPrincipal 확인)
    public ResponseEntity<MemberResponse> modify(@RequestBody ModifyInfoRequest request) {
        MemberResponse response = memberService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/withdrawal") //탈퇴 구현 -> 탈퇴 시 "/logout"으로 이동
    public ResponseEntity<?> withdrawal(@RequestBody WithdrawalRequest request, HttpServletResponse response) throws IOException {
        //@AuthenticationPrincipal Member member, member.getEmail()
        boolean result = memberService.withdrawal(request);
        if (result) {
            //프론트에서 처리하는 것이 적절(ResponseEntity 과 충돌 우려)
            response.sendRedirect("/logout");
            return ResponseEntity.ok().body(new SuccessResult("성공", "정상적으로 탈퇴 되었습니다."));
        }
        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResult("실패", "탈퇴 중 문제가 발생하였습니다."));
    }

    //유저 정보 조회
    @GetMapping("/userinfo")
    public ResponseEntity<UserInfoResponse> userInfo(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok().body(memberService.userInfo(email));
    }
}
