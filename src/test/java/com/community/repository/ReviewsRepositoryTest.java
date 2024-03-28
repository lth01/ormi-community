package com.community.repository;

import com.community.domain.entity.Companies;
import com.community.domain.entity.Member;
import com.community.domain.entity.Reviews;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ReviewsRepositoryTest {

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Autowired
    private CompaniesRepository companiesRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void save() {
        Companies companies = companiesRepository.findAll().get(0);
        Member member = memberRepository.findAll().get(0);

        Reviews reviews = Reviews.builder()
                .reviewId(UUID.randomUUID().toString())
                .reviewTitle("리뷰 제목")
                .reviewContent("리뷰 내용")
                .companies(companies)
                .reviewWriter(member)
                .build();
        reviewsRepository.save(reviews);

        Assertions.assertEquals(reviews.getReviewId(), reviewsRepository.findAll().get(0).getReviewId());
    }

}