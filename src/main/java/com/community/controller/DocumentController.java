package com.community.controller;

import com.community.domain.dto.*;
import com.community.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class DocumentController {

    private DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    //slice 조회
    @GetMapping("/document/list/{board_id}")
    public ResponseEntity<List<FindDocumentResponse>> showAllDocument(
            @PathVariable("board_id") String boardId,
            @PageableDefault(size = 10) Pageable pageable) {
        List<FindDocumentResponse> list = documentService.findAllByBoard(boardId, pageable);
        return ResponseEntity.ok().body(list);
    }

    //단건 조회
    @GetMapping("/document/{document_id}")
    public ResponseEntity<FindDocumentResponse> showOneDocument(@PathVariable("document_id") String documentId) {
        FindDocumentResponse response = documentService.findOneDocument(documentId);
        return ResponseEntity.ok().body(response);
    }

    //게시글 작성
    @PostMapping("/document/manage")
    public ResponseEntity<DocumentWriteResponse> saveDocument(@RequestBody AddDocumentRequest request, Authentication authentication) {
        String email = Optional.ofNullable(authentication.getName()).orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
        DocumentWriteResponse response = documentService.saveDocument(email, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //게시글 수정
    @PutMapping("/document/manage/{document_id}")
    public ResponseEntity<DocumentWriteResponse> modifyDocument(@PathVariable("document_id") String documentId,@RequestBody ModifyDocumentRequest request, Authentication authentication) {
        String email = Optional.ofNullable(authentication.getName()).orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
        DocumentWriteResponse response = documentService.modifyDocument(email,documentId,request);
        return ResponseEntity.ok().body(response);
    }


    //게시글 삭제
    @DeleteMapping("/document/manage/{document_id}")
    public ResponseEntity<DocumentWriteResponse> deleteDocument(@PathVariable("document_id") String documentId, Authentication authentication) {
        String email = Optional.ofNullable(authentication.getName()).orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
        DocumentWriteResponse response = documentService.deleteDocument(email, documentId);
        return ResponseEntity.ok().body(response);
    }

    //게시글 좋아요
    @PutMapping("/document/{document_id}/like")
    public ResponseEntity<DocumentWriteResponse> increaseDocumentLike(@PathVariable("document_id") String documentId) {
        DocumentWriteResponse response = documentService.increaseDocumentLike(documentId);
        return ResponseEntity.ok().body(response);
    }
}
