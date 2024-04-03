package com.community.domain.dto;

import com.community.domain.entity.Board;
import com.community.domain.entity.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocWritePageResponse {
    private String docTitle;
    private String docContent;
    private String docBoardId;
    private String docBoardName;


    public DocWritePageResponse(Document document) {
        docTitle = document.getDocTitle();
        docContent = document.getDocContent();
        docBoardId = document.getBoard().getBoardId();
        docBoardName = document.getBoard().getBoardName();
    }

}
