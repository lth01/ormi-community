package com.community.repository;

import com.community.domain.entity.Industry;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class IndustryRepositoryTest {

    @Autowired
    private IndustryRepository industryRepository;

    @Test
    public void save() {
        Industry industry = new Industry(UUID.randomUUID().toString(), "컴퓨터 프로그래밍", "컴퓨터 시스템을 통합 구축하는 산업활동과 컴퓨터 시스템의 관리 및 운영관련 기술서비스를 주로 제공하는 산업활동을 말한다.");
        Industry industry1 = new Industry(UUID.randomUUID().toString(), "정보서비스업", "정보처리, 호스팅 서비스 및 온라인 정보제공 서비스를 제공하는 산업활동이 포함된다. 뉴스제공 등의 기타 정보 서비스활동도 여기에 분류한다.");

        industryRepository.save(industry1);

        String str = industryRepository.findById(industry1.getIndustryId()).orElseThrow().getIndustryName();
        Assertions.assertEquals(industry1.getIndustryName(), str);
        log.info(str);
    }
}