package com.community.repository;

import com.community.domain.entity.Member;
import com.community.domain.entity.Report;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.util.SubnetUtils;
import org.junit.jupiter.api.Assertions;
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

    @Test
    public void save() {
        Member member = memberRepository.findAll().get(0);

        Report report = Report.builder()
                .reportId(UUID.randomUUID().toString())
                .reporterIp("127.0.0.1")
                .reportContent("뚱뚱함")
                .reporter(member)
                .build();

        reportRepository.save(report);

        Assertions.assertEquals(report.getReportId(), reportRepository.findAll().get(0).getReportId());
    }
}