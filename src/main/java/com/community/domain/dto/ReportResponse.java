package com.community.domain.dto;

import com.community.domain.entity.Member;
import com.community.domain.entity.Report;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
    private String reporterIp;
    private String reportContent;
    private Member reporter;
    private LocalDateTime reportDate;


    public ReportResponse(Report report) {
        reporterIp = report.getReporterIp();
        reportContent = report.getReportContent();
        reporter = report.getReporter();
        reportDate = report.getReportDate();
    }
}
