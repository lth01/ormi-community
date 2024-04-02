package com.community.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.community.domain.dto.AddIndustryRequest;
import com.community.repository.IndustryRepository;
import org.apache.commons.lang3.RandomStringUtils;
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

	@Autowired
	private IndustryRepository industryRepository;

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
		String boardId = boardRepository.findAll().get(0).getBoardId();
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

	@Test
	@DisplayName("업종 추가 테스트 이름이 중복하거나 업종명 혹은 설명의 크기가 제한을 넘을 경우 오류")
	void saveIndustry() throws Exception{
		//given1
		String dupIndustryName = industryRepository.findAll().getFirst().getIndustryName();
		String exccedLengthName = "123456789012345678901";
		String exccedLengthComment = RandomStringUtils.randomAlphanumeric(260);

		AddIndustryRequest request1 = new AddIndustryRequest(dupIndustryName);
		AddIndustryRequest request2 = new AddIndustryRequest(exccedLengthName);
		AddIndustryRequest request3 = new AddIndustryRequest("123029", exccedLengthComment);

		String requestStr1 = objectMapper.writeValueAsString(request1);
		String requestStr2 = objectMapper.writeValueAsString(request2);
		String requestStr3 = objectMapper.writeValueAsString(request3);

		//when
		ResultActions resultActions1 = mockMvc.perform(post("/admin/industry")
				.content(requestStr1)
				.contentType(MediaType.APPLICATION_JSON));
		ResultActions resultActions2 = mockMvc.perform(post("/admin/industry")
				.content(requestStr2)
				.contentType(MediaType.APPLICATION_JSON));
		ResultActions resultActions3 = mockMvc.perform(post("/admin/industry")
				.content(requestStr3)
				.contentType(MediaType.APPLICATION_JSON));

		//then
		resultActions1.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("message").value("이미 존재하는 업종입니다."));
		resultActions2.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("message").value("업종 이름은 20자 이상 입력될 수 없습니다."));
		resultActions3.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("message").value("업종 코멘트는 255자를 초과할 수 없습니다."));
	}
}
