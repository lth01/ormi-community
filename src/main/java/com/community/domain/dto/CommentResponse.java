package com.community.domain.dto;


import com.community.domain.entity.Comment;
import com.community.domain.entity.Document;
import com.community.domain.entity.LikeIt;
import com.community.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponse {

    private String commentId;

    private String commentCreatorIp;

    private String commentContent;

    //relation with doc_id
    private Document document;

    //relation with comment_creator
    private Member commentCreator;

    //relation with like_it
    private LikeIt likeIt;

    public CommentResponse(Comment comment) {
        commentId = comment.getCommentId();
        commentCreatorIp = comment.getCommentCreatorIp();
        commentContent = comment.getCommentContent();
        commentCreator = comment.getCommentCreator();
        document = comment.getDocument();
        likeIt = comment.getLikeIt();
    }
}
