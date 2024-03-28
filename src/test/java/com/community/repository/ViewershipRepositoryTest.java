package com.community.repository;

import com.community.domain.entity.Viewership;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ViewershipRepositoryTest {

    @Autowired
    private ViewershipRepository viewershipRepository;

    @Test
    public void clearAll() {
        viewershipRepository.deleteAll();
    }
    @Test
    public void save() {
        Viewership viewership = new Viewership(UUID.randomUUID().toString(), 0L);

        viewershipRepository.save(viewership);
        Assertions.assertEquals(viewership.getViewId(),viewershipRepository.findAll().get(0).getViewId());
    }
}