package com.community.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import javax.xml.transform.Result;

import com.community.domain.entity.Comment;
import com.community.domain.entity.Document;
import com.community.repository.CommentRepository;
import com.community.repository.DocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("좋아요 관련 테스트")
public class LikeItControllerTest {
	private static final Logger log = LoggerFactory.getLogger(LikeItControllerTest.class);
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	private DocumentRepository documentRepository;
	@Autowired
	private CommentRepository commentRepository;

	public LikeItControllerTest() {
	}

	@BeforeEach
	public void mockMvcSetUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	@DisplayName("좋아요 카운트를 조회합니다. 댓글, 게시글 아이디만 유효합니다.")
	void search() throws Exception {
		//given
		String docUUID = documentRepository.findAll().getFirst().getDocId();
		String commentUUID = commentRepository.findAll().getFirst().getCommentId();
		String notValidUUID = UUID.randomUUID().toString();

		//when
		ResultActions resultActions1 = mockMvc.perform(get("/likeit/" + docUUID));
		ResultActions resultActions2 = mockMvc.perform(get("/likeit/" + commentUUID));
		ResultActions resultActions3 = mockMvc.perform(get("/likeit/" + notValidUUID));

		//then
		resultActions1.andExpect(status().isOk())
			.andDo(print());
		resultActions2.andExpect(status().isOk())
			.andDo(print());
		resultActions3.andExpect(status().is4xxClientError());
	}

	@Test
	@DisplayName("좋아요 카운트를 증가시킵니다.")
	void update() throws Exception{
		//given
		String docId = documentRepository.findAll().getFirst().getDocId();
		String commentId = commentRepository.findAll().getFirst().getCommentId();
		String notValidUUID = UUID.randomUUID().toString();

		//when
		ResultActions resultActions1 = mockMvc.perform(put("/likeit/" + docId));
		ResultActions resultActions2 = mockMvc.perform(put("/likeit/" + commentId));
		ResultActions resultActions3 = mockMvc.perform(put("/likeit/" +  notValidUUID));

		//then
		resultActions1.andExpect(status().isOk());
		resultActions2.andExpect(status().isOk());
		resultActions3.andExpect(status().is4xxClientError());
	}
}

