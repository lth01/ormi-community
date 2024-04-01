package com.community.controller;

import com.community.domain.dto.CreateBoardRequest;
import com.community.domain.dto.ErrorResult;
import com.community.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {
    private BoardService boardService;

    @PostMapping("/board")
    public ResponseEntity<ErrorResult> requestCreateBoard(@RequestBody CreateBoardRequest body){
        boolean isSuccess = boardService.saveBoard(body);

    }
}
