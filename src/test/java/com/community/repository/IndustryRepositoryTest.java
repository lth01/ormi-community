package com.community.repository;

import com.community.domain.entity.Industry;
import lombok.extern.slf4j.Slf4j;
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
class IndustryRepositoryTest {

    @Autowired
    private IndustryRepository industryRepository;

    private Industry industry;
    private Industry industry1;

    @BeforeEach
    public void setUp() {
        industry = new Industry(UUID.randomUUID().toString(),
                "영림업",
                "임목을 생산하기 위하여 산림에서 나무를 심고, 가꾸고, 보호하는 산업활동을 말한다.");
        industry1 = new Industry(UUID.randomUUID().toString(),
                "기록매체 복제업",
                "범용성  소프트웨어  및  필름의  복제품을  생산하는  산업활동을  말한다.");
    }

    @AfterEach
    public void clear() {
        industryRepository.delete(industry);
        industryRepository.delete(industry1);
    }

    @Test
    public void save() {
        industryRepository.save(industry);
        industryRepository.save(industry1);

        String str = industryRepository.findById(industry1.getIndustryId()).orElseThrow().getIndustryName();
        Assertions.assertEquals(industry1.getIndustryName(), str);
        log.info(str);
    }
}