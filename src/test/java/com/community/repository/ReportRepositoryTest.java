package com.community.repository;

import com.community.domain.entity.Member;
import com.community.domain.entity.MemberRole;
import com.community.domain.entity.PasswordQuestion;
import com.community.domain.entity.Report;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.util.SubnetUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ReportRepositoryTest {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private MemberRepository memberRepository;
    private Member member;
    private PasswordQuestion question;
    private MemberRole memberRole;
    private Report report;
    @BeforeEach
    public void setUp() {
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

        report = Report.builder()
                .reportId(UUID.randomUUID().toString())
                .reporterIp("127.0.0.1")
                .reportContent("뚱뚱함")
                .reporter(member)
                .build();
    }
    @AfterEach
    public void clear() {
        reportRepository.delete(report);
    }

    @Test
    public void save() {
        reportRepository.save(report);

        Assertions.assertEquals(report.getReportId(), reportRepository.findAll().get(0).getReportId());
    }
}