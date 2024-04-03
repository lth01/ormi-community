package com.community.service;

import com.community.domain.dto.*;
import com.community.domain.entity.Industry;
import com.community.domain.entity.Member;
import com.community.domain.entity.MemberInterests;
import com.community.domain.entity.MemberRole;
import com.community.repository.MemberInterestsRepository;
import com.community.repository.MemberRepository;
import com.community.repository.MemberRoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class MemberService {

    private MemberRepository memberRepository;
    private MemberRoleRepository memberRoleRepository;
    private MemberInterestsRepository memberInterestsRepository;
    private PasswordEncoder encoder;

    public MemberService(MemberRepository memberRepository, MemberRoleRepository memberRoleRepository, MemberInterestsRepository memberInterestsRepository, PasswordEncoder encoder) {
        this.memberRepository = memberRepository;
        this.memberRoleRepository = memberRoleRepository;
        this.memberInterestsRepository = memberInterestsRepository;
        this.encoder = encoder;
    }

    //회원가입
    @Transactional
    public MemberResponse save(AddMemberRequest request) {
        //UUID 자동 생성
        String createUUID = UUID.randomUUID().toString();
        //기본 USER 권한 추가
        MemberRole USER = memberRoleRepository.findAllByMemberRoleName("USER").orElseThrow();

        //닉네임 유효성 검사
        if(memberRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("이미 등록된 닉네임 입니다.");
        }
        //이메일 유효성 검사
        if(memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 등록된 이메일 입니다.");
        }

        //앞자리 한 글자로 변환
        if(request.getGender().length() > 1) {
            request.setGender(request.getGender().substring(0,1));
        }

        //회원 정보 저장
        Member member = memberRepository.save(Member.builder()
                .memberId(createUUID)
                .name(request.getName())
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .gender(request.getGender())
                .phone(request.getPhone())
                .passwordQuestion(request.getPasswordQuestion())
                .findPasswordAnswer(request.getFindPasswordAnswer())
                .memberRole(USER)
                .build());

        //관심 업종 저장
        for(Industry industry : request.getIndustries()) {
            MemberInterests interest = new MemberInterests(
                    UUID.randomUUID().toString(),
                    industry,
                    member);
            memberInterestsRepository.save(interest);
        }

        return new MemberResponse(member);
    }

    @Transactional
    public MemberResponse update(String email, ModifyInfoRequest request) {

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("잘못된 사용자 입니다."));

        if (!member.getEmail().equals(email)) throw new RuntimeException("수정은 본인만 가능 합니다.");

        //빈공간 및 같은 값은 수정 하지 않음.
        if(!(request.getNickname().isEmpty() || request.getNickname().isBlank() || request.getNickname().equals(member.getNickname()))) {
            member.setNickname(request.getNickname());
        }
        if(!(request.getPassword().isBlank() || request.getPassword().isEmpty() || encoder.matches(member.getPassword(), request.getPassword()))){
            member.setPassword(encoder.encode(request.getPassword()));
        }
        if(!(request.getPhone().isBlank() || request.getPhone().isEmpty() || request.getPhone().equals(member.getPhone()))) {
            member.setPhone(request.getPhone());
        }
        if(!(request.getPasswordQuestion() == null || request.getPasswordQuestion().equals(member.getPasswordQuestion()))) {
            member.setPasswordQuestion(request.getPasswordQuestion());
        }
        if(!(request.getFindPasswordAnswer().isBlank() || request.getFindPasswordAnswer().isEmpty() || request.getFindPasswordAnswer().equals(member.getFindPasswordAnswer()))) {
            member.setFindPasswordAnswer(request.getFindPasswordAnswer());
        }


        //관심 업종 저장
        List<MemberInterests> list = memberInterestsRepository.findAllByMember(member);
        for(Industry industry : request.getIndustries()) {
            int count = 0;
            for(MemberInterests interests : list) {
                if(interests.getIndustry().equals(industry)) continue;
                else if (list.size() + count > 3) memberInterestsRepository.delete(interests);

                MemberInterests interest = new MemberInterests(
                        UUID.randomUUID().toString(),
                        industry,
                        member);
                memberInterestsRepository.save(interest);
                count++;
            }

        }
        return new MemberResponse(member);
    }

    @Transactional
    public boolean withdrawal(String email, WithdrawalRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("잘못된 입력 입니다."));

        if (member.getEmail().equals(email)) throw new RuntimeException("삭제는 본인만 가능 합니다.");

        if(encoder.matches(request.getPassword(), member.getPassword())) {
            member.setWithdrawal(true);
            memberRepository.updateWithdrawal(member.getWithdrawal(), member.getEmail());
            return true;
        }
        return false;
    }

    public UserInfoResponse userInfo(String email) {
        if (email == null || email.isEmpty()) throw new RuntimeException("비회원 입니다.");
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자 입니다."));
        return new UserInfoResponse(member);
    }
}
