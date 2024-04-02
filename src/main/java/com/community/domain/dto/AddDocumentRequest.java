package com.community.domain.dto;

import com.community.domain.entity.Board;
import com.community.domain.entity.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddDocumentRequest {
    private String docTitle;
    private String docContent;
    private Board board;

    public AddDocumentRequest(Document document) {
        docTitle = document.getDocTitle();
        docContent = document.getDocContent();
        board = document.getBoard();
    }
}