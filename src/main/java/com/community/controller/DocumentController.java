package com.community.controller;

import com.community.domain.dto.AddDocumentRequest;
import com.community.domain.dto.DocumentResponse;
import com.community.domain.dto.DocumentWriteResponse;
import com.community.domain.dto.ModifyDocumentRequest;
import com.community.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class DocumentController {

    private DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    //slice 조회
    @GetMapping("/board/list/{board_id}")
    public ResponseEntity<List<DocumentResponse>> showAllDocument(
            @PathVariable("board_id") String boardId,
            @PageableDefault(size = 10) Pageable pageable) {
        List<DocumentResponse> list = documentService.findAllByBoard(boardId, pageable);
        return ResponseEntity.ok().body(list);
    }

    //단건 조회
    @GetMapping("/board/{document_id}")
    public ResponseEntity<DocumentResponse> showOneDocument(@PathVariable("document_id") String documentId) {
        DocumentResponse response = documentService.findOneDocument(documentId);
        return ResponseEntity.ok().body(response);
    }

    //작성 페이지 이동
    @GetMapping("/document/{document_id}")
    public ResponseEntity<DocumentWriteResponse> showWriteDocument(@PathVariable(required = false, name = "document_id") String documentId) {
        DocumentWriteResponse response = documentService.showWriteDocument(documentId);
        return ResponseEntity.ok().body(response);
    }
    //게시글 작성
    @PostMapping("/document")
    public ResponseEntity<DocumentWriteResponse> saveDocument(@RequestBody AddDocumentRequest request, Authentication authentication) {
        String email = authentication.getName();
        DocumentWriteResponse response = documentService.saveDocument(email, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //게시글 수정
    @PutMapping("/document/{document_id}")
    public ResponseEntity<DocumentWriteResponse> modifyDocument(@PathVariable("document_id") String documentId,@RequestBody ModifyDocumentRequest request, Authentication authentication) {
        String email = authentication.getName();
        DocumentWriteResponse response = documentService.modifyDocument(email,documentId,request);
        return ResponseEntity.ok().body(response);
    }


    //게시글 삭제
    @DeleteMapping("/document/{document_id}")
    public ResponseEntity<DocumentWriteResponse> deleteDocument(@PathVariable("document_id") String documentId, Authentication authentication) {
        String email = authentication.getName();
        DocumentWriteResponse response = documentService.deleteDocument(email, documentId);
        return ResponseEntity.ok().body(response);
    }

    //게시글 좋아요
    @PutMapping("/board/{document_id}/like")
    public ResponseEntity<DocumentWriteResponse> increaseDocumentLike(@PathVariable("document_id") String documentId) {
        DocumentWriteResponse response = documentService.increaseDocumentLike(documentId);
        return ResponseEntity.ok().body(response);
    }
}
