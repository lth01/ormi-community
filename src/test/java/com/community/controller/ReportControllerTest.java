package com.community.controller;

import com.community.domain.dto.ReportRequest;
import com.community.domain.entity.*;
import com.community.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MemberRoleRepository memberRoleRepository;
    @Autowired
    private ReportRepository reportRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        MemberRole role = memberRoleRepository.findAllByMemberRoleName("ADMIN").orElseThrow(() -> new RuntimeException("없음"));
        member = memberRepository.findAllByMemberRole(role).orElseThrow(() -> new RuntimeException("NO")).get(0);
    }


    @Test
    void reportDocument() throws Exception {
        Document document = documentRepository.findAll().get(0);
        ReportRequest request = new ReportRequest("욕설");

        ResultActions resultActions = mockMvc.perform(post("/report/doc/" + document.getDocId())
                .with(SecurityMockMvcRequestPostProcessors.user(member))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.reportContent").value("욕설")).andDo(print());
    }

    @Test
    void reportComment() throws Exception {
        Comment comment = commentRepository.findAll().get(0);
        ReportRequest request = new ReportRequest("욕설");

        ResultActions resultActions = mockMvc.perform(post("/report/com/" + comment.getCommentId())
                .with(SecurityMockMvcRequestPostProcessors.user(member))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.reportContent").value("욕설")).andDo(print());
    }

    @Test
    void acceptReport() throws Exception {
        Report report = reportRepository.findAll().get(0);

        ResultActions resultActions = mockMvc.perform(put("/admin/report/accept/" + report.getReportId())
                .with(SecurityMockMvcRequestPostProcessors.user(member)));

        resultActions.andExpect(status().isOk())
                .andDo(print());
    }
}