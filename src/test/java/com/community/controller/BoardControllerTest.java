package com.community.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

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

import com.community.domain.dto.CreateBoardRequest;
import com.community.repository.CompaniesRepository;
import com.community.repository.IndustryRepository;
import com.community.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class BoardControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext webApplicationContext;

	// Dependencies
	@Autowired
	private IndustryRepository industryRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private CompaniesRepository companiesRepository;

	@BeforeEach
	public void mockMvcSetUp(){
		this.mockMvc = MockMvcBuilders
			.webAppContextSetup(webApplicationContext)
			.build();
	}

	@Test
	@DisplayName("게시판 생성 요청을 보낸다. 문제가 없을경우 오류 코드와 문구가 모두 정상적으로 동작했다 반환된다.")
	void create() throws Exception {
		//given
		String industryId = industryRepository.findAll().getFirst().getIndustryId();
		String comId = companiesRepository.findAll().getFirst().getComId();
		String requesterId = memberRepository.findAll().getFirst().getMemberId();
		CreateBoardRequest request = new CreateBoardRequest(industryId, comId, requesterId);
		//to json string body
		String jsonStr = objectMapper.writeValueAsString(request);

		//when
		ResultActions resultActions = mockMvc.perform(post("/board")
			.content(jsonStr)
			.contentType(MediaType.APPLICATION_JSON));

		//then
		resultActions.andExpect(status().isCreated())
			.andExpect(jsonPath("code").value("성공"));
	}

	@Test
	@DisplayName("회사 Id가 회사 DB에 없을경우 게시판 생성 요청을 보냈을때")
	void createExceptComId() throws Exception{
		//given
		String industryId = industryRepository.findAll().getFirst().getIndustryId();
		String comId = UUID.randomUUID().toString();
		String requesterId = memberRepository.findAll().getFirst().getMemberId();
		CreateBoardRequest request = new CreateBoardRequest(industryId, comId, requesterId);
		//to json string body
		String jsonStr = objectMapper.writeValueAsString(request);

		//when
		ResultActions resultActions = mockMvc.perform(post("/board")
			.content(jsonStr)
			.contentType(MediaType.APPLICATION_JSON));

		//then
		resultActions.andExpect(status().isCreated())
			.andExpect(jsonPath("code").value("성공"));
	}

}

