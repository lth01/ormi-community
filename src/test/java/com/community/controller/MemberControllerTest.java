package com.community.controller;

import com.community.domain.dto.AddMemberRequest;
import com.community.domain.dto.ModifyInfoRequest;
import com.community.domain.dto.WithdrawalRequest;
import com.community.domain.entity.Industry;
import com.community.domain.entity.Member;
import com.community.domain.entity.PasswordQuestion;
import com.community.repository.IndustryRepository;
import com.community.repository.MemberRepository;
import com.community.repository.PasswordQuestionRepository;
import com.community.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PasswordQuestionRepository passwordQuestionRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private IndustryRepository industryRepository;


    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }


    @Test
    void signup() throws Exception {
        //given
        PasswordQuestion question = passwordQuestionRepository.findByQuestion("좋아하는 동물은?").orElseThrow();
        List<Industry> industries = new ArrayList<>();
        industries.add(industryRepository.findAll().get(0));
        AddMemberRequest request = new AddMemberRequest("김요한", "두두새", "test2@test.com", "1234", "M", "010-1234-1234", question, "도도새", industries);
        String json = objectMapper.writeValueAsString(request);




        //when
        ResultActions resultActions = mockMvc.perform(post("/member/register")
                .content(json)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("email").value(request.getEmail()));
    }

    @Test
    public void modifyMemberInfo() throws Exception {
        //given
        PasswordQuestion question = passwordQuestionRepository.findByQuestion("좋아하는 동물은?").orElseThrow();
        List<Industry> industries = new ArrayList<>();
        industries.add(industryRepository.findAll().get(0));

        ModifyInfoRequest request = new ModifyInfoRequest(
                "test@test.com",
                "도도새",
                "1234",
                "01012345678",
                question,
                "도도새",
                industries
        );
        String json = objectMapper.writeValueAsString(request);

        //when
        ResultActions resultActions = mockMvc.perform(put("/member/modifyInfo")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk());

    }

    @Test
    public void withdrawalMember() throws Exception {
        //given
        WithdrawalRequest withdrawalRequest = new WithdrawalRequest("test2@test.com", "1234");
        String json = objectMapper.writeValueAsString(withdrawalRequest);

        //when
        ResultActions resultActions = mockMvc.perform(put("/member/withdrawal")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk());
    }
}