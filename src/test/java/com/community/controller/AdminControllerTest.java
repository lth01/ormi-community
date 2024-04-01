package com.community.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.community.domain.dto.UpdateBoardApproveRequest;
import com.community.repository.BoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@DisplayName("어드민 관련 API 테스트")
public class AdminControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext webApplicationContext;

	// Dependencies
	@Autowired
	private BoardRepository boardRepository;

	@BeforeEach
	public void mockMvcSetUp(){
		this.mockMvc = MockMvcBuilders
			.webAppContextSetup(webApplicationContext)
			.build();
	}

	@Test
	@DisplayName("게시판 승인 여부 테스트")
	void updateBoardStatus() throws Exception{
		/**
		 * case1: ? -> true
		 * case2: ? -> false
		 */
		//give
		String boardId = boardRepository.findAll().getFirst().getBoardId();
		UpdateBoardApproveRequest request1 = new UpdateBoardApproveRequest(boardId, true);
		UpdateBoardApproveRequest request2 = new UpdateBoardApproveRequest(boardId, false);

		//to json string body
		String jsonStr1 = objectMapper.writeValueAsString(request1);
		String jsonStr2 = objectMapper.writeValueAsString(request2);

		//when
		//then
		//case1: OK, 성공, 조회시 값 true
		//case2: OK, 성공, 조회시 값 false
		ResultActions resultActions1 = mockMvc.perform(put("/admin/board")
			.content(jsonStr1)
			.contentType(MediaType.APPLICATION_JSON));

		resultActions1
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("성공"));

		assertThat(boardRepository.findById(boardId).get().getApprove()).isSameAs(true);

		ResultActions resultActions2 = mockMvc.perform(put("/admin/board")
			.content(jsonStr2)
			.contentType(MediaType.APPLICATION_JSON));

		resultActions2
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("성공"));
		assertThat(boardRepository.findById(boardId).get().getApprove()).isSameAs(false);
	}
}
