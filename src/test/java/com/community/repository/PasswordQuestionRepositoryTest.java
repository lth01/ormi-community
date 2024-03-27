package com.community.repository;

import com.community.domain.entity.PasswordQuestion;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
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

    @Test
    public void save() {
        PasswordQuestion question = new PasswordQuestion(UUID.randomUUID().toString(), "좋아하는 동물은?");
        log.info("저장할 객체 : " + question.getQuestion());
        passwordQuestionRepository.save(question);

        PasswordQuestion savedQuestion = passwordQuestionRepository.findById(question.getPasswordQuestionId()).orElseThrow();
        log.info("저장된 객체 : " + savedQuestion.getQuestion());
        Assertions.assertEquals(question.getQuestion(), savedQuestion.getQuestion());

    }
}