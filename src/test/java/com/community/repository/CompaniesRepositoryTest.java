package com.community.repository;

import com.community.domain.entity.Companies;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class CompaniesRepositoryTest {

    @Autowired
    private CompaniesRepository companiesRepository;

    private Companies company;
    @BeforeEach
    public void setUp() {
        company = new Companies(UUID.randomUUID().toString(), "1101111129497", "카카오");
    }
    @AfterEach
    public void clear() {
        companiesRepository.delete(company);
    }

    @Test
    public void save() {

        companiesRepository.save(company);

        Assertions.assertEquals(company.getComName(), companiesRepository.findById(company.getComId()).orElseThrow().getComName());
    }

    @Test
    public void findByName() {
        companiesRepository.save(company);
        Companies company = companiesRepository.findByComName("카카오").orElseThrow();
        log.info(company.getComName());
    }
}