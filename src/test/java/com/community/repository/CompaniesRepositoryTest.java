package com.community.repository;

import com.community.domain.entity.Companies;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class CompaniesRepositoryTest {

    @Autowired
    private CompaniesRepository companiesRepository;

    @Test
    public void save() {
        Companies company = new Companies(UUID.randomUUID().toString(), "1101111129497", "카카오");

        companiesRepository.save(company);

        Assertions.assertEquals(company.getComName(), companiesRepository.findById(company.getComId()).orElseThrow().getComName());
    }

    @Test
    public void findByName() {
        Companies company = companiesRepository.findByComName("카카오").orElseThrow();
        log.info(company.getComName());
    }
}