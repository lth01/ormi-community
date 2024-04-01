package com.community.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.community.domain.dto.ErrorResult;
import com.community.domain.dto.UpdateBoardApproveRequest;
import com.community.domain.entity.Board;
import com.community.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
public class AdminController {
	private BoardService boardService;

	/**
	 * 실제 admin 여부 검사는 filter에서 수행한다.
	 * 이유: body에서 전달하는 파라미터는 탈취의 위험이 존재함
	 */
	@PutMapping("/admin/board")
	public ResponseEntity<ErrorResult> updateBoardStatus(@RequestBody UpdateBoardApproveRequest request){
		boardService.updateApprove(request);

		return ResponseEntity.status(HttpStatus.OK)
			.body(new ErrorResult("성공", "정상적으로 수정하였습니다."));
	}
}
