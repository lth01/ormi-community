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
import com.community.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    private WebApplicationContext context;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    private PasswordQuestionRepository passwordQuestionRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private IndustryRepository industryRepository;

    private String TEST_EMAIL = "test@test.com";
    private String TEST_PASSWORD = "1234";
    private Member member;
    private HttpHeaders httpHeaders;

    @BeforeEach
    void SetUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();

        member = memberRepository.findByEmail(TEST_EMAIL).orElseThrow();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(TEST_EMAIL, TEST_PASSWORD);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, Object> claimMap = Map.of("email", TEST_EMAIL);

        String jwt = jwtUtil.generateToken(claimMap, 1);

        httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);
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
                .with(SecurityMockMvcRequestPostProcessors.user(member))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(httpHeaders));

        //then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("email").value(request.getEmail()));
    }

    @Test
    void modifyMemberInfo() throws Exception {
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


        //when
        ResultActions resultActions = mockMvc.perform(put("/member/modifyInfo")
                .with(SecurityMockMvcRequestPostProcessors.user(member))
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .headers(httpHeaders));

        //then
        resultActions.andExpect(status().isOk()).andDo(print());

    }

    @Test
    void withdrawalMember() throws Exception {
        //given
        WithdrawalRequest withdrawalRequest = new WithdrawalRequest("test2@test.com", "1234");
        String json = objectMapper.writeValueAsString(withdrawalRequest);

        //when
        ResultActions resultActions = mockMvc.perform(put("/member/withdrawal")
                .with(SecurityMockMvcRequestPostProcessors.user(member))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(httpHeaders));

        //then
        resultActions.andExpect(status().isOk());
    }
}