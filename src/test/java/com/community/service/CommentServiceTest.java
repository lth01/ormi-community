package com.community.service;

import com.community.domain.dto.*;
import com.community.domain.entity.Comment;
import com.community.domain.entity.Document;
import com.community.domain.entity.LikeIt;
import com.community.domain.entity.Member;
import com.community.repository.CommentRepository;
import com.community.repository.DocumentRepository;
import com.community.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
@Slf4j
class CommentServiceTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CommentService commentService;

    @Test
    void findAllByDocs() {
        //given
        Document document = documentRepository.findAll().get(0);

        //when
        List<FindCommentResponse> responses = commentService.findAllByDocs(document.getDocId());

        //then
        for (FindCommentResponse response : responses) {
            log.info(response.getCommentContent());
        }
    }

    @Test
    void save() {
        Document document = documentRepository.findAll().get(0);
        AddCommentRequest request = new AddCommentRequest(null, "127.0.0.1", "댓글 내용", null);

        CommentCommonResponse comment = commentService.saveComment("test@test.com",document.getDocId(),request);

        log.info(comment.getCommentContent());
    }

    @Test
    void update() {
        //given
        Member member = memberRepository.findByEmail("test1@test.com").orElseThrow();
        Comment comment1 = commentRepository.findAllByCommentCreator(member).orElseThrow().get(0);
        ModifyCommentRequest modifyCommentRequest = new ModifyCommentRequest(null, "내용 수정");

        //when
        CommentCommonResponse comment = commentService.updateComment("test1@test.com", comment1.getCommentId(), modifyCommentRequest);

        //then
        Assertions.assertEquals(modifyCommentRequest.getCommentContent(), comment.getCommentContent());
    }

    @Test
    void delete() {
        //given
        Member member = memberRepository.findByEmail("test1@test.com").orElseThrow();
        Comment comment1 = commentRepository.findAllByCommentCreator(member).orElseThrow().get(0);
        DeleteCommentRequest request = new DeleteCommentRequest(null);

        //when
        CommentCommonResponse comment = commentService.deleteComment("test1@test.com", comment1.getCommentId(), request);

        //then
        Assertions.assertFalse(commentRepository.existsById(comment.getCommentId()));
    }

    @Test
    void increaseLike() {
        //given
        Member member = memberRepository.findByEmail("test1@test.com").orElseThrow();
        Comment comment1 = commentRepository.findAllByCommentCreator(member).orElseThrow().get(0);



        //when
        CommentCommonResponse comment = commentService.increaseCommentLike(comment1.getCommentId());

    }
}