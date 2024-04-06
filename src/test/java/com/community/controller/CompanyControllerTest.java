package com.community.controller;

import com.community.domain.entity.Companies;
import com.community.repository.CompaniesRepository;
import com.community.repository.ViewershipRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyControllerTest {

    @Autowired
    private CompaniesRepository companiesRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private Companies companies;

    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();

        companies = Companies.builder()
                .comId(UUID.randomUUID().toString())
                .comName("삼성전자")
                .regNum("1301110006246")
                .build();
    }

    @AfterEach
    public void clear() {
        companiesRepository.delete(companies);
    }

    @Test
    void bringCompanyDataById() throws Exception {
        companiesRepository.save(companies);

        ResultActions resultActions = mockMvc.perform(get("/companydata/" + companies.getComId()));

        resultActions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void bringCompanyDataByName() throws Exception {
        companiesRepository.save(companies);

        ResultActions resultActions = mockMvc.perform(get("/companydata/name/" + companies.getComName()));

        resultActions.andExpect(status().isOk()).andDo(print());
    }
}