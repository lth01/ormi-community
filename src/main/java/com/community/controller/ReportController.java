package com.community.controller;

import com.community.domain.dto.ReportRequest;
import com.community.domain.dto.ReportResponse;
import com.community.service.ReportService;
import jakarta.servlet.http.HttpServletRequest;
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
public class ReportController {

    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    //모두 조회
    @GetMapping("/admin/report")
    public ResponseEntity<List<ReportResponse>> showAllReport(@PageableDefault(size = 10) Pageable pageable) {
        List<ReportResponse> response = reportService.showAllReport(pageable);

        return ResponseEntity.ok().body(response);
    }

    //게시글 신고
    @PostMapping("/report/doc/{document_id}")
    public ResponseEntity<ReportResponse> reportDocument(@PathVariable("document_id") String documentId, @RequestBody ReportRequest request
            , Authentication authentication, HttpServletRequest servletRequest) {
        String email = authentication.getName();
        String userIp = servletRequest.getRemoteAddr();
        ReportResponse response = reportService.reportDocument(email, documentId, userIp, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //댓글 신고
    @PostMapping("/report/com/{comment_id}")
    public ResponseEntity<ReportResponse> reportComment(@PathVariable("comment_id") String commentId, @RequestBody ReportRequest request
            , Authentication authentication, HttpServletRequest servletRequest) {
        String email = authentication.getName();
        String userIp = servletRequest.getRemoteAddr();
        ReportResponse response = reportService.reportComment(email, commentId, userIp, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //신고 승인
    @PutMapping("/admin/report/accept/{report_id}")
    public ResponseEntity<ReportResponse> acceptReport(@PathVariable("report_id")String reportId) {
        ReportResponse response = reportService.acceptReport(reportId);
        return ResponseEntity.ok().body(response);
    }
}
