package com.community.service;

import com.community.domain.dto.ReportRequest;
import com.community.domain.dto.ReportResponse;
import com.community.domain.entity.Comment;
import com.community.domain.entity.Document;
import com.community.domain.entity.Member;
import com.community.domain.entity.Report;
import com.community.repository.CommentRepository;
import com.community.repository.DocumentRepository;
import com.community.repository.MemberRepository;
import com.community.repository.ReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ReportService {

    private ReportRepository reportRepository;
    private CommentRepository commentRepository;
    private DocumentRepository documentRepository;
    private MemberRepository memberRepository;

    public ReportService(ReportRepository reportRepository, CommentRepository commentRepository, DocumentRepository documentRepository, MemberRepository memberRepository) {
        this.reportRepository = reportRepository;
        this.commentRepository = commentRepository;
        this.documentRepository = documentRepository;
        this.memberRepository = memberRepository;
    }

    public List<ReportResponse> showAllReport(Pageable pageable) {
        try {
            Slice<Report> reports = reportRepository.findAll(pageable);
            return reports.getContent().stream().map(ReportResponse::new).toList();
        } catch (Exception e) {
            throw new RuntimeException("현재 신고가 없습니다.");
        }
    }

    @Transactional
    public ReportResponse reportDocument(String email, String documentId,String userIp, ReportRequest request) {
        String uuid = UUID.randomUUID().toString();
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new RuntimeException("존재하지 않는 사용자 입니다."));
        Document document = documentRepository.findById(documentId).orElseThrow(()->new RuntimeException("존재하지 않는 게시글 입니다."));

        Report report = Report.builder()
                .reportId(uuid)
                .reporterIp(userIp)
                .reportContent(request.getReportContent())
                .reporter(member)
                .reportType(1L)
                .build();

        //해당 게시글에 report 저장
        document.setReport(report);

        return new ReportResponse(reportRepository.save(report));
    }

    @Transactional
    public ReportResponse reportComment(String email, String commentId,String userIp, ReportRequest request) {
        String uuid = UUID.randomUUID().toString();
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new RuntimeException("존재하지 않는 사용자 입니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new RuntimeException("존재하지 않는 댓글 입니다."));

        Report report = Report.builder()
                .reportId(uuid)
                .reporterIp(userIp)
                .reportContent(request.getReportContent())
                .reporter(member)
                .reportType(2L)
                .build();

        //해당 댓글에 report 저장
        comment.setReport(report);

        return new ReportResponse(reportRepository.save(report));
    }

    @Transactional
    public ReportResponse acceptReport(String reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new RuntimeException("해당 신고가 존재하지 않습니다."));

        if (report.getReportType() == 1L) {
            Document document = documentRepository.findByReport(report).orElseThrow(()->new RuntimeException("존재하지 않는 게시글 입니다."));
            document.setDocVisible(true);
            report.setReportJudge(true);
            return new ReportResponse(report);
        } else if (report.getReportType() == 2L) {
            Comment comment = commentRepository.findByReport(report).orElseThrow(()->new RuntimeException("존재하지 않는 댓글 입니다."));
            comment.setCommentVisible(true);
            report.setReportJudge(true);
            return new ReportResponse(report);
        }
        throw new IllegalArgumentException("잘못된 형식의 신고 입니다.");
    }

}
