package com.community.repository;

import com.community.domain.entity.Board;
import com.community.domain.entity.Companies;
import com.community.domain.entity.Industry;
import com.community.domain.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private CompaniesRepository companiesRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void save() {
        Industry industry = industryRepository.findAll().get(0);
        Companies companies = companiesRepository.findAll().get(0);
        Member member = memberRepository.findAll().get(0);

        Board board = Board.builder()
                .boardId(UUID.randomUUID().toString())
                .industry(industry)
                .companies(companies)
                .memberAdmin(member)
                .memberUser(member)
                .approve(true)
                .build();

        boardRepository.save(board);

        log.info(board.getBoardId()
        + "  " + board.getIndustry().getIndustryName()
        + "  " + board.getMemberUser().getName()
        + "  " + board.getCompanies().getComName()
                );
    }
}