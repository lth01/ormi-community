package com.community.controller;

import com.community.domain.entity.Companies;
import com.community.repository.CompaniesRepository;
import com.community.repository.ViewershipRepository;
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

    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();

//        Companies companies = Companies.builder()
//                .comId(UUID.randomUUID().toString())
//                .comName("카카오")
//                .regNum("1101111129497")
//                .build();
//        companiesRepository.save(companies);
    }

    @Test
    void bringCompanyDataById() throws Exception {

        Companies companies = companiesRepository.findAll().get(0);

        ResultActions resultActions = mockMvc.perform(get("/companydata/" + companies.getComId()));

        resultActions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void bringCompanyDataByName() throws Exception {
        Companies companies = companiesRepository.findAll().get(0);

        ResultActions resultActions = mockMvc.perform(get("/companydata/name/" + companies.getComName()));

        resultActions.andExpect(status().isOk()).andDo(print());
    }
}