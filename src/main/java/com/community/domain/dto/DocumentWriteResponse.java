package com.community.domain.dto;

import com.community.domain.entity.Board;
import com.community.domain.entity.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentWriteResponse {
    private String docId;
    private String docTitle;
    private String docContent;
    private Board board;

    public DocumentWriteResponse(Document document) {
        docId = document.getDocId();
        docTitle = document.getDocTitle();
        docContent = document.getDocContent();
        board = document.getBoard();
    }
}
