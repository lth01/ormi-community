package com.community.repository;

import com.community.domain.entity.ReviewCategories;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ReviewCategoriesRepositoryTest {

    @Autowired
    private ReviewCategoriesRepository reviewCategoriesRepository;

    @Test
    public void save() {
        ReviewCategories categories = new ReviewCategories(UUID.randomUUID().toString(), "자유로운 분위기");
        ReviewCategories categories1 = new ReviewCategories(UUID.randomUUID().toString(), "풍부한 경험");

        reviewCategoriesRepository.save(categories);
        reviewCategoriesRepository.save(categories1);

        Assertions.assertEquals(categories.getReviewCategoriesId(), reviewCategoriesRepository.findAll().get(0).getReviewCategoriesId());
        Assertions.assertEquals(categories1.getReviewCategoriesId(), reviewCategoriesRepository.findAll().get(1).getReviewCategoriesId());
    }
}