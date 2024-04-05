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
    public void testData() {
        List<PasswordQuestion> list = passwordQuestionRepository.findAll();
        PasswordQuestion question = list.get(0);

        List<MemberRole> list1 = authoritiesRepository.findAll();
        MemberRole authorities = list1.get(0);

        Member member0;
        member0 = Member.builder()
                .memberId("4eed02a1-a986-4e6b-a48c-c67238797fab")
                .name("김요한")
                .nickname("도도새")
                .email("test@test.com")
                .password("1234")
                .gender("M")
                .phone("010-1234-5678")
                .findPasswordAnswer("도도새")
                .passwordQuestion(question)
                .memberRole(authorities)
                .build();
        Member member1;
        member1 = Member.builder()
                .memberId("829629cf-5414-44de-9c8f-38848d9a2481")
                .name("김요한")
                .nickname("도도새1")
                .email("test1@test.com")
                .password("1234")
                .gender("M")
                .phone("010-1234-5678")
                .findPasswordAnswer("도도새")
                .passwordQuestion(question)
                .memberRole(authorities)
                .build();
        Member member2;
        member2 = Member.builder()
                .memberId("e8b3862f-30b8-4a52-83b9-2ed5fa922233")
                .name("김요한")
                .nickname("도도새2")
                .email("test2@test.com")
                .password("1234")
                .gender("M")
                .phone("010-1234-5678")
                .findPasswordAnswer("도도새")
                .passwordQuestion(question)
                .memberRole(authorities)
                .build();

        memberRepository.save(member0);
        memberRepository.save(member1);
        memberRepository.save(member2);

    }
    @Test
    public void save() {
        List<PasswordQuestion> list = passwordQuestionRepository.findAll();
        PasswordQuestion question = list.get(0);

        List<MemberRole> list1 = authoritiesRepository.findAll();
        MemberRole authorities = list1.get(0);

        for (int i = 3 ; i < 20 ; i++) {
            Member member;
                member = Member.builder()
                        .memberId(UUID.randomUUID().toString())
                        .name("김요한")
                        .nickname("도도새" + i)
                        .email("test" + i + "@test.com")
                        .password("1234")
                        .gender("M")
                        .phone("010-1234-5678")
                        .findPasswordAnswer("도도새")
                        .passwordQuestion(question)
                        .memberRole(authorities)
                        .build();
            memberRepository.save(member);
        }
    }

    @Test
    public void findByEmailTest() {
        Member test = memberRepository.findByEmail("test@test.com").orElseThrow();
        Assertions.assertEquals("test@test.com",test.getEmail());
        log.info(test.getEmail() + "  " + test.getPassword());
    }

    @Test
    public void deleteOne() {
        Member member = memberRepository.findByEmail("test@test.com").orElseThrow();
        memberRepository.delete(member);
    }
}