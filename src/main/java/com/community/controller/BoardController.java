package com.community.controller;

import com.community.domain.dto.CreateBoardRequest;
import com.community.domain.dto.ErrorResult;
import com.community.domain.entity.Board;
import com.community.service.BoardService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BoardController {
    private BoardService boardService;

    @PostMapping("/board")
    public ResponseEntity<ErrorResult> requestCreateBoard(@RequestBody CreateBoardRequest body){
        boardService.saveBoard(body);
        ErrorResult result = new ErrorResult("성공", "성공적으로 생성 요청을 완료하였습니다.");

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
