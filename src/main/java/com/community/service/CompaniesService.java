package com.community.service;

import com.community.repository.CompaniesRepository;
import org.springframework.stereotype.Service;

@Service
public class CompaniesService {

    private CompaniesRepository companiesRepository;

    public CompaniesService(CompaniesRepository companiesRepository) {
        this.companiesRepository = companiesRepository;
    }

    //API 확인 해야함

}
