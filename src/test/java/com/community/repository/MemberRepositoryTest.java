package com.community.repository;

import com.community.domain.entity.Member;
import com.community.domain.entity.MemberRole;
import com.community.domain.entity.PasswordQuestion;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Slf4j
class MemberRepositoryTest {

    @Autowired
    public MemberRepository memberRepository;

    @Autowired
    public PasswordQuestionRepository passwordQuestionRepository;

    @Autowired
    public MemberRoleRepository authoritiesRepository;

    //테스트 이후 모든 회원 테이블 삭제
//    @AfterEach
//    public void clearData() {
//        memberRepository.deleteAll();
//    }

    @Test
    public void save() {
        List<PasswordQuestion> list = passwordQuestionRepository.findAll();
        PasswordQuestion question = list.get(0);

        List<MemberRole> list1 = authoritiesRepository.findAll();
        MemberRole authorities = list1.get(0);

        Member member = Member.builder()
                .memberId(UUID.randomUUID().toString())
                .name("김요한")
                .nickname("도도새")
                .email("test@test.com")
                .password("test")
                .gender("M")
                .phone("010-1234-5678")
                .findPasswordAnswer("도도새")
                .passwordQuestion(question)
                .memberRole(authorities)
                .build();

        memberRepository.save(member);

        Assertions.assertEquals(member.getMemberId(), memberRepository.findById(member.getMemberId()).orElseThrow().getMemberId());

        log.info(member.getMemberId()
                + "  " + member.getNickname()
                + "  " + member.getEmail()
                + "  " + member.getPassword()
                + "  " + member.getGender()
                + "  " + member.getPhone()
                + "  " + member.getFindPasswordAnswer()
                + "  " + member.getPasswordQuestion()
                + "  " + member.getPasswordQuestion().getQuestion()
                + "  " + member.getAuthorities()
                );
    }

    @Test
    public void findByEmailTest() {
        Member test = memberRepository.findByEmail("test@test.com").orElseThrow();
        Assertions.assertEquals("test@test.com",test.getEmail());
        log.info(test.getEmail() + "  " + test.getPassword());
    }

    @Test
    public void deleteOne() {
        Member member = memberRepository.findByEmail("test1@test.com").orElseThrow();
        memberRepository.delete(member);
    }
}