package com.community.repository;

import com.community.domain.entity.Industry;
import com.community.domain.entity.Member;
import com.community.domain.entity.MemberInterests;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class MemberInterestsRepositoryTest {

    @Autowired
    private MemberInterestsRepository memberInterestsRepository;

    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void save() {
        Industry industry = industryRepository.findAll().get(0);
        Member member = memberRepository.findAll().get(0);
        MemberInterests interests = new MemberInterests(UUID.randomUUID().toString(), industry, member);

        log.info(interests.getInterestsId()
        + "  " + interests.getIndustry().getIndustryName()
        + "  " + interests.getMember().getName()
                );
        memberInterestsRepository.save(interests);
    }

    @Test
    public void findByMember() {
        Member member = memberRepository.findAll().get(0);

        List<MemberInterests> list = memberInterestsRepository.findAllByMember(member).orElse(new ArrayList<>());

        for(MemberInterests interests : list) {
            log.info(interests.getIndustry().getIndustryName());
        }
    }

//    @AfterEach
//    public void clearAll() {
//        memberInterestsRepository.deleteAll();
//    }
}