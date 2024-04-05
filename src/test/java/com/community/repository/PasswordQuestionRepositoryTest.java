package com.community.repository;

import com.community.domain.entity.PasswordQuestion;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class PasswordQuestionRepositoryTest {

    @Autowired
    public PasswordQuestionRepository passwordQuestionRepository;
    private PasswordQuestion question;
    @BeforeEach
    public void setUp() {
        question = new PasswordQuestion(UUID.randomUUID().toString(), "좋아하는 음식은?");
    }

    @AfterEach
    public void clear() {
        passwordQuestionRepository.delete(question);
    }

    @Test
    public void save() {
        passwordQuestionRepository.save(question);

        PasswordQuestion savedQuestion = passwordQuestionRepository.findById(question.getPasswordQuestionId()).orElseThrow();
        log.info("저장된 객체 : " + savedQuestion.getQuestion());
        Assertions.assertEquals(question.getQuestion(), savedQuestion.getQuestion());

    }
}