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
public class Document {

    @Id
    @Column(name = "doc_id")
    private String docId;

    @Column(name = "doc_title", nullable = false)
    private String docTitle;

    @Column(name = "doc_content", nullable = false)
    private String docContent;

    @CreatedDate
    @Column(name = "doc_create_date")
    private LocalDateTime docCreateDate;

    @LastModifiedDate
    @Column(name = "doc_mod_date")
    private LocalDateTime docModDate;

    @Column(name = "doc_visible", nullable = false)
    private Boolean docVisible;

    //relation with doc_creator(Member)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_creator", nullable = false)
    private Member docCreator;

    //relation with doc_modifier(Member)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_modifier")
    private Member docModifier;

    //relation with board
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    //relation with industry
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industry_id", nullable = false)
    private Industry industry;

    //relation with companies
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "com_id", nullable = false)
    private Companies companies;

    //relation with like_it
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_like_id", nullable = false)
    private LikeIt likeIt;

    //relation with viewership
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_view_id", nullable = false)
    private Viewership viewership;

    //relation with report
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private Report report;


    @PrePersist
    public void prePersist() {
        docVisible = docVisible == null ? false : docVisible;
    }
}
