package com.community.service;

import com.community.domain.dto.CreateBoardRequest;
import com.community.domain.entity.Board;
import com.community.domain.entity.Companies;
import com.community.domain.entity.Industry;
import com.community.domain.entity.Member;
import com.community.repository.BoardRepository;
import com.community.repository.CompaniesRepository;
import com.community.repository.IndustryRepository;
import com.community.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class BoardService {
    private BoardRepository boardRepository;
    private IndustryRepository industryRepository;
    private CompaniesRepository companiesRepository;
    private MemberRepository memberRepository;
    @Transactional
    public Board saveBoard(CreateBoardRequest body) {
        //UUID 생성
        String createUUID = UUID.randomUUID().toString();

        //업종 객체 확인
        Optional<Industry> industry = industryRepository.findById(body.getIndustryId());

        //기업 DB에 있는지 확인(현재는 가데이터 혹은 수작업으로 처리 이후 API를 통해 자동으로 DB에 주입)
        Optional<Companies> companies = companiesRepository.findById(body.getComId());

        //멤버가 실제 조회 되는지
        Optional<Member> member = memberRepository.findById(body.getRequesterId());

        //회사 데이터는 실제로 없을 수 있음

        //업종 코드가 존재하는지 검사
        if(industry.isEmpty()){
            throw new IllegalArgumentException("유효하지 않은 업종 ID입니다.");
        }

        //멤버 코드가 존재하는지 검사
        if(member.isEmpty()){
            throw new IllegalArgumentException("유효하지 않은 회원입니다.");
        }

        Board board = boardRepository.save(Board.builder()
                        .boardId(createUUID)
                        .industry(industry.get())
                        .companies(companies.orElse(null))
                        .memberUser(member.get())
                        //승인 대기
                        .approve(false)
                        .build()
        );

        return board;
    }
}


