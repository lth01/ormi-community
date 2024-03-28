package com.community.repository;

import com.community.domain.entity.LikeIt;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class LikeItRepositoryTest {

    @Autowired
    private LikeItRepository likeItRepository;

    @Test
    public void save() {
        LikeIt likeIt = new LikeIt(UUID.randomUUID().toString(), 0L);

        likeItRepository.save(likeIt);
        Assertions.assertEquals(likeIt.getLikeId(), likeItRepository.findById(likeIt.getLikeId()).orElseThrow().getLikeId());
    }
}