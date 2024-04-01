package com.community.repository;

import com.community.domain.entity.Member;
import com.community.domain.entity.MemberRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
@Slf4j
class MemberRoleRepositoryTest {

    @Autowired
    public MemberRoleRepository authoritiesRepository;

    @Autowired
    public MemberRepository memberRepository;

    @Test
    public void save() {
        MemberRole authorities = new MemberRole(UUID.randomUUID().toString(), "ADMIN");

        log.info(authorities.getMemberRoleName());

        authoritiesRepository.save(authorities);
        String str = authoritiesRepository.findById(authorities.getMemberRoleId()).orElseThrow().getMemberRoleName();
        log.info(str);

        Assertions.assertEquals(authorities.getMemberRoleName(), str);
    }
    @Test
    public void clearAll() {
        authoritiesRepository.deleteAll();
    }
}