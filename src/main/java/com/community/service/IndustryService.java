package com.community.service;

import com.community.domain.entity.Industry;
import com.community.repository.IndustryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class IndustryService {
    private IndustryRepository industryRepository;

    /**
     * @biref
     * @return 목록
     */
    public List<Industry> showAllIndustry(){
        return industryRepository.findAll();
    }
}
