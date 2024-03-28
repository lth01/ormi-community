package com.community.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Report {

    @Id
    @Column(name = "report_id")
    private String reportId;

    @Column(name = "reporter_ip")
    private String reporterIp;

    @Column(name = "report_content", nullable = false)
    private String reportContent;

    @CreatedDate
    @Column(name = "report_date")
    private LocalDateTime reportDate;

    @Column(name = "report_judge", nullable = false)
    private Boolean reportJudge;

    //relation with doc_creator(Member)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private Member reporter;

    @PrePersist
    public void prePersist() {
        reportJudge = reportJudge == null ? false : reportJudge;
    }
}
