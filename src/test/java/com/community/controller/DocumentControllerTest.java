package com.community.controller;

import com.community.domain.dto.AddDocumentRequest;
import com.community.domain.dto.ModifyDocumentRequest;
import com.community.domain.entity.Board;
import com.community.domain.entity.Document;
import com.community.domain.entity.Member;
import com.community.repository.*;
import com.community.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class DocumentControllerTest {

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
    private BoardRepository boardRepository;
    private Member member;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    private String TEST_EMAIL = "test@test.com";
    private String TEST_PASSWORD = "1234";

    private HttpHeaders httpHeaders;


    @BeforeEach
    void setUp() {
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
    void showAllDocument() throws Exception {
        Board board = boardRepository.findAll().get(0);

        ResultActions resultActions = mockMvc.perform(get("/document/list/" + board.getBoardId() + "?page=0")
                .with(SecurityMockMvcRequestPostProcessors.user(member)));

        resultActions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void saveDocument() throws Exception {
        Board board = boardRepository.findAll().get(0);

        AddDocumentRequest request = new AddDocumentRequest("게시글 제목", "게시글 내용", board);

        ResultActions resultActions = mockMvc.perform(post("/document/manage")
                .with(SecurityMockMvcRequestPostProcessors.user(member))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .headers(httpHeaders));

        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.docTitle").value(request.getDocTitle())).andDo(print());
    }

    @Test
    void modifyDocument() throws Exception {
        Document document = documentRepository.findAll().get(0);

        ModifyDocumentRequest request = new ModifyDocumentRequest("제목 수정", "내용 수정", member);

        ResultActions resultActions = mockMvc.perform(put("/document/manage/" + document.getDocId())
                .with(SecurityMockMvcRequestPostProcessors.user(member))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .headers(httpHeaders));

        resultActions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void deleteDocument() throws Exception {
        Document document = documentRepository.findAllByDocCreator(member).get(0);

        ResultActions resultActions = mockMvc.perform(delete("/document/manage/" + document.getDocId())
                .with(SecurityMockMvcRequestPostProcessors.user(member))
                .headers(httpHeaders));

        resultActions.andExpect(status().isOk()).andDo(print());
    }
}