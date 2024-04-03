package com.community.controller;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.community.domain.entity.Viewership;
import com.community.repository.ViewershipRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@DisplayName("뷰어십 관련 테스트")
public class ViewershipControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	// Dependencies
	@Autowired
	private ViewershipRepository viewershipRepository;

	@BeforeEach
	public void mockMvcSetUp(){
		this.mockMvc = MockMvcBuilders
			.webAppContextSetup(webApplicationContext)
			.build();
	}

	@Test
	@DisplayName("조회수를 조회합니다. 조회한 조회수는 repository에서 가져온 값과 일치해야합니다.")
	void search() throws Exception{
		//given
		Viewership viewership = viewershipRepository.findAll().getFirst();
		String uuid = viewership.getViewId();

		//when
		ResultActions resultActions = mockMvc.perform(get("/viewership/" + uuid));

		//then
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("viewCount").value(viewership.getViewCount()));
	}

	@Test
	@DisplayName("조회수를 1증가시킵니다. 문제가 발생할 경우 400Error가 발생합니다.")
	void increase() throws Exception{
		//given
		String validUUID = viewershipRepository.findAll().getFirst().getViewId();
		String notValidUUID = UUID.randomUUID().toString();

		//when
		ResultActions resultActions1 = mockMvc.perform(put("/viewership/" + validUUID));
		ResultActions resultActions2 = mockMvc.perform(put("/viewership/" + notValidUUID));

		//then
		resultActions1.andExpect(status().isOk());
		resultActions2.andExpect(status().is4xxClientError());
	}

}
