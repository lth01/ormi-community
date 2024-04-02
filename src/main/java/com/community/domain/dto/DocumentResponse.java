package com.community.domain.dto;

import com.community.domain.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentResponse {
    private String docId;
    private String docTitle;
    private String docContent;
    private LocalDateTime docCreateDate;
    private Member docCreator;
    private Board board;
    private Companies companies;
    private LikeIt likeIt;
    private Viewership viewership;

    public DocumentResponse(Document document) {
        docId = document.getDocId();
        docTitle = document.getDocTitle();
        docContent = document.getDocContent();
        docCreateDate = document.getDocCreateDate();
        docCreator = document.getDocCreator();
        board = document.getBoard();
        companies = document.getCompanies();
        likeIt = document.getLikeIt();
        viewership = document.getViewership();
    }
}
