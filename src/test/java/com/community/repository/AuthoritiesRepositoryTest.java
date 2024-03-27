package com.community.repository;

import com.community.domain.entity.Authorities;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class AuthoritiesRepositoryTest {

    @Autowired
    public AuthoritiesRepository authoritiesRepository;

    @Test
    public void save() {
        Authorities authorities = new Authorities(UUID.randomUUID().toString(), "USER");
        log.info(authorities.getAuthority());

        authoritiesRepository.save(authorities);
        String str = authoritiesRepository.findById(authorities.getAuthorityId()).orElseThrow().getAuthorityName();
        log.info(str);

        Assertions.assertEquals(authorities.getAuthorityName(), str);
    }
}