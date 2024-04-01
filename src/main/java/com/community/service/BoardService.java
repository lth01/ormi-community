package com.community.service;

import com.community.repository.BoardRepository;
import com.community.repository.IndustryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class BoardService {
    private BoardRepository boardRepository;
    private IndustryRepository industryRepository;
}


