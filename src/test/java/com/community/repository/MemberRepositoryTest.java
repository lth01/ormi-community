package com.community.repository;

import com.community.domain.entity.Member;
import com.community.domain.entity.MemberRole;
import com.community.domain.entity.PasswordQuestion;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    private MemberRoleRepository memberRoleRepository;

    @Autowired
    public PasswordQuestionRepository passwordQuestionRepository;

    @Autowired
    public MemberRoleRepository authoritiesRepository;

    private PasswordQuestion question;
    private MemberRole memberRole;
    private Member member;

    @BeforeEach
    public void setUp() {
        //권한 주입
        try {
            memberRole = new MemberRole(UUID.randomUUID().toString(), "USER");
        } catch (Exception e) {memberRole = memberRoleRepository.findAllByMemberRoleName("USER").orElseThrow();}
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
    }
    @AfterEach
    public void clear() {
        memberRepository.delete(member);
    }



    @Test
    public void testData() {

//        member = Member.builder()
//                .memberId("4eed02a1-a986-4e6b-a48c-c67238797fab")
//                .name("김요한")
//                .nickname("도도새")
//                .email("test@test.com")
//                .password("1234")
//                .gender("M")
//                .phone("010-1234-5678")
//                .findPasswordAnswer("도도새")
//                .passwordQuestion(question)
//                .memberRole(memberRole)
//                .build();
//        Member member1;
//        member1 = Member.builder()
//                .memberId("829629cf-5414-44de-9c8f-38848d9a2481")
//                .name("김요한")
//                .nickname("도도새1")
//                .email("test1@test.com")
//                .password("1234")
//                .gender("M")
//                .phone("010-1234-5678")
//                .findPasswordAnswer("도도새")
//                .passwordQuestion(question)
//                .memberRole(memberRole)
//                .build();
//        Member member2;
//        member2 = Member.builder()
//                .memberId("e8b3862f-30b8-4a52-83b9-2ed5fa922233")
//                .name("김요한")
//                .nickname("도도새2")
//                .email("test2@test.com")
//                .password("1234")
//                .gender("M")
//                .phone("010-1234-5678")
//                .findPasswordAnswer("도도새")
//                .passwordQuestion(question)
//                .memberRole(memberRole)
//                .build();

        memberRepository.save(member);
//        memberRepository.save(member1);
//        memberRepository.save(member2);
    }
//    @Test
//    public void save() {
//
//        for (int i = 3 ; i < 20 ; i++) {
//            Member member;
//                member = Member.builder()
//                        .memberId(UUID.randomUUID().toString())
//                        .name("김요한")
//                        .nickname("도도새" + i)
//                        .email("test" + i + "@test.com")
//                        .password("1234")
//                        .gender("M")
//                        .phone("010-1234-5678")
//                        .findPasswordAnswer("도도새")
//                        .passwordQuestion(question)
//                        .memberRole(memberRole)
//                        .build();
//            memberRepository.save(member);
//        }
//    }

    @Test
    public void findByEmailTest() {
        memberRepository.save(member);
        Member test = memberRepository.findByEmail(member.getEmail()).orElseThrow();
        Assertions.assertEquals(member.getEmail(),test.getEmail());
        log.info(test.getEmail() + "  " + test.getPassword());
    }

    @Test
    public void deleteOne() {
        memberRepository.save(member);
        memberRepository.delete(member);
    }
}