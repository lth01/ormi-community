package com.community.repository;

import com.community.domain.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class MemberInterestsRepositoryTest {

    @Autowired
    private MemberInterestsRepository memberInterestsRepository;

    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private MemberRepository memberRepository;
    private MemberInterests interests;
    private Industry industry;

    private Member member;
    private PasswordQuestion question;
    private MemberRole memberRole;
    @BeforeEach
    public void setUp() {
        industry = new Industry(UUID.randomUUID().toString(),
                "영림업",
                "임목을 생산하기 위하여 산림에서 나무를 심고, 가꾸고, 보호하는 산업활동을 말한다.");
        //권한 주입
        memberRole = new MemberRole(UUID.randomUUID().toString(), "USER");
        //질문 주입
        question = new PasswordQuestion(UUID.randomUUID().toString(), "좋아하는 음식은?");
        //회원 주입
        member = Member.builder()
                .memberId("4eed02a1-a986-4e6b-a48c-c67238797fab")
                .name("김요한")
                .nickname("요들레히")
                .email("test@com.com")
                .password("1234")
                .gender("M")
                .phone("010-1234-5678")
                .findPasswordAnswer("도도새")
                .passwordQuestion(question)
                .memberRole(memberRole)
                .build();

        interests = new MemberInterests(UUID.randomUUID().toString(), industry, member);
    }
    @AfterEach
    public void clear() {
        memberInterestsRepository.delete(interests);
    }

    @Test
    public void save() {
        memberInterestsRepository.save(interests);

        log.info(interests.getInterestsId()
                + "  " + interests.getIndustry().getIndustryName()
                + "  " + interests.getMember().getName()
        );
    }

    @Test
    public void findByMember() {
        memberInterestsRepository.save(interests);

        List<MemberInterests> list = memberInterestsRepository.findAllByMember(member).orElse(new ArrayList<>());
        for(MemberInterests interests : list) {
            log.info(interests.getIndustry().getIndustryName());
        }
    }
}