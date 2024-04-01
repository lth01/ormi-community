package com.community.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
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

import com.community.repository.BoardRepository;
import com.community.repository.CompaniesRepository;
import com.community.repository.IndustryRepository;
import com.community.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@DisplayName("게시판 생성 & 승인 관련 테스트")
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
		String boardname = "이스트소프트";
		String industryId = industryRepository.findAll().getFirst().getIndustryId();
		String comId = companiesRepository.findAll().getFirst().getComId();
		String requesterId = memberRepository.findAll().getFirst().getMemberId();
		CreateBoardRequest request = new CreateBoardRequest(boardname,industryId, comId, requesterId);
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
		String boardName = "이스트소프트2";
		String industryId = industryRepository.findAll().getFirst().getIndustryId();
		String comId = UUID.randomUUID().toString();
		String requesterId = memberRepository.findAll().getFirst().getMemberId();
		CreateBoardRequest request = new CreateBoardRequest(boardName,industryId, comId, requesterId);
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
	@DisplayName("회사 이름을 중복으로 삽입하여 익셉션이 나는지 확인")
	void createDuplicateBoardName() throws Exception{
		//given
		String dupBoardName = UUID.randomUUID().toString().substring(0, 20);
		String industryId = industryRepository.findAll().getFirst().getIndustryId();
		String comId1 = UUID.randomUUID().toString();
		String comId2 = UUID.randomUUID().toString();
		String requesterId = memberRepository.findAll().getFirst().getMemberId();

		CreateBoardRequest request1 = new CreateBoardRequest(dupBoardName, industryId, comId1, requesterId);
		CreateBoardRequest request2 = new CreateBoardRequest(dupBoardName, industryId, comId2, requesterId);
		//to json string body
		String jsonStr1 = objectMapper.writeValueAsString(request1);
		String jsonStr2 = objectMapper.writeValueAsString(request2);

		//when
		ResultActions resultActions1 = mockMvc.perform(post("/board")
			.content(jsonStr1)
			.contentType(MediaType.APPLICATION_JSON));

		ResultActions resultActions2 = mockMvc.perform(post("/board")
			.content(jsonStr2)
			.contentType(MediaType.APPLICATION_JSON));
		//then

		//resultActions 1은 정상 생성 HTTPSTATUS.CREATED
		//resultActions 2는 오류 발생 400
		resultActions1.andExpect(status().isCreated());
		resultActions2.andExpect(status().is4xxClientError());
	}

	@Test
	@DisplayName("회사 참조 중복 여부 확인")
	void createDuplicateCompanies() throws Exception {
		//given
		String boardName1 = "이스트소프트3";
		String boardName2 = "이스트소프트4";
		String industryId = industryRepository.findAll().getFirst().getIndustryId();
		String comId = companiesRepository.findAll().getFirst().getComId();
		String requesterId = memberRepository.findAll().getFirst().getMemberId();
		CreateBoardRequest request1 = new CreateBoardRequest(boardName1,industryId, comId, requesterId);
		CreateBoardRequest request2 = new CreateBoardRequest(boardName2,industryId, comId, requesterId);

		//to json string body
		String jsonStr1 = objectMapper.writeValueAsString(request1);
		String jsonStr2 = objectMapper.writeValueAsString(request2);

		//when
		ResultActions resultActions1 = mockMvc.perform(post("/board")
			.content(jsonStr1)
			.contentType(MediaType.APPLICATION_JSON));

		ResultActions resultActions2 = mockMvc.perform(post("/board")
			.content(jsonStr2)
			.contentType(MediaType.APPLICATION_JSON));

		//then
		//resultActions 1은 정상 생성 HTTPSTATUS.CREATED
		//resultActions 2는 오류 발생 400
		resultActions1.andExpect(status().isCreated());
		resultActions2.andExpect(status().is4xxClientError());
	}

	@Test
	@DisplayName("현재 게시판 목록을 조회한다. 허용여부가 false라면 조회하지 않는다.")
	void searchBoardExceptNotApprove() throws Exception{
		//given

		//when
		ResultActions resultActions1 = mockMvc.perform(get("/board"));

		//then
		resultActions1.andExpect(status().isOk())
			.andDo(print());
	}
}

