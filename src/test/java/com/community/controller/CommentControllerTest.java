package com.community.controller;

import com.community.domain.dto.AddCommentRequest;
import com.community.domain.dto.DeleteCommentRequest;
import com.community.domain.dto.ModifyCommentRequest;
import com.community.domain.entity.Comment;
import com.community.domain.entity.Document;
import com.community.domain.entity.Member;
import com.community.repository.CommentRepository;
import com.community.repository.DocumentRepository;
import com.community.repository.LikeItRepository;
import com.community.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WithMockUser
@SpringBootTest
@Slf4j
public class CommentControllerTest {

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
    private LikeItRepository likeItRepository;

    private Member member;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();
        member = memberRepository.findByEmail("test2@test.com").orElseThrow();
    }

    @Test
    void showCommentAll() throws Exception {

        Document document = documentRepository.findAllByDocCreator(member).orElseThrow().get(0);

        ResultActions resultActions = mockMvc.perform(get("/comment/list/" + document.getDocId()));

        resultActions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    void saveComment() throws Exception {
        Document document = documentRepository.findAllByDocCreator(member).orElseThrow().get(0);
        AddCommentRequest request = new AddCommentRequest(null, "127.0.0.1", "Test comment", "");


        mockMvc.perform(post("/comment/" + document.getDocId())
                        .with(SecurityMockMvcRequestPostProcessors.user(member))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void modifyComment() throws Exception {
        Member member = memberRepository.findByEmail("test@test.com").orElseThrow();
        Comment comment = commentRepository.findAllByCommentCreator(member).orElseThrow().get(0);
        ModifyCommentRequest request = new ModifyCommentRequest(null, "내용 수정!");

        ResultActions resultActions = mockMvc.perform(put("/comment/" + comment.getCommentId())
                .with(SecurityMockMvcRequestPostProcessors.user(member))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        resultActions.andExpect(status().isOk()).andDo(print());

    }

    @Test
    void deleteComment() throws Exception {
        Member member = memberRepository.findByEmail("test@test.com").orElseThrow();
        Comment comment = commentRepository.findAllByCommentCreator(member).orElseThrow().get(0);
        DeleteCommentRequest request = new DeleteCommentRequest(null);

        ResultActions resultActions = mockMvc.perform(put("/comment/" + comment.getCommentId())
                .with(SecurityMockMvcRequestPostProcessors.user(member))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        resultActions.andExpect(status().isOk()).andDo(print());

    }

    @Test
    void likeComment() throws Exception {
        Member member = memberRepository.findByEmail("test@test.com").orElseThrow();
        Comment comment = commentRepository.findAllByCommentCreator(member).orElseThrow().get(0);
        log.info(comment.getCommentId());
        ResultActions resultActions = mockMvc.perform(put("/comment/" + comment.getCommentId() + "/like"));

        resultActions.andExpect(status().isOk()).andDo(print());
    }
}