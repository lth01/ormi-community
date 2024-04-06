package com.community.repository;

import com.community.domain.entity.MemberRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
@Slf4j
class MemberRoleRepositoryTest {

    @Autowired
    public MemberRoleRepository memberRoleRepository;

    @Autowired
    public MemberRepository memberRepository;

    private MemberRole authorities;

    @BeforeEach
    public void setUp() {
        authorities = new MemberRole(UUID.randomUUID().toString(), "USER");
    }
    @AfterEach
    public void clear() {
        memberRoleRepository.delete(authorities);
    }
    @Test
    public void save() {
        memberRoleRepository.save(authorities);
        String str = memberRoleRepository.findById(authorities.getMemberRoleId()).orElseThrow().getMemberRoleName();

        Assertions.assertEquals(authorities.getMemberRoleName(), str);
    }
}