package com.community.repository;

import com.community.domain.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LikeItRepository likeItRepository;

    @Test
    public void clearAll() {
        commentRepository.deleteAll();
    }

    @Test
    public void save() {
        Document document = documentRepository.findAll().get(0);
        Member member = memberRepository.findAll().get(0);

        Comment comment = Comment.builder()
                .commentId(UUID.randomUUID().toString())
                .commentCreatorIp("127.0.0.1")
                .commentContent("댓글 내용")
                .document(document)
                .commentCreator(member)
                .likeIt(likeItRepository.save(new LikeIt(UUID.randomUUID().toString(), 0L)))
                .build();

        commentRepository.save(comment);

        Assertions.assertEquals(comment.getCommentId(), commentRepository.findAll().get(0).getCommentId());
    }
}