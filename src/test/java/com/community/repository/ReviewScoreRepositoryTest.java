package com.community.repository;

import com.community.domain.entity.ReviewCategories;
import com.community.domain.entity.ReviewScore;
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
class ReviewScoreRepositoryTest {

    @Autowired
    private ReviewScoreRepository reviewScoreRepository;

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Autowired
    private ReviewCategoriesRepository reviewCategoriesRepository;

    @Test
    public void save() {
        Reviews reviews = reviewsRepository.findAll().get(0);
        ReviewCategories categories = reviewCategoriesRepository.findAll().get(0);

        ReviewScore score = new ReviewScore(UUID.randomUUID().toString(), 3, reviews, categories);

        reviewScoreRepository.save(score);

        Assertions.assertEquals(score.getScoreId(), reviewScoreRepository.findAll().get(0).getScoreId());
    }
}