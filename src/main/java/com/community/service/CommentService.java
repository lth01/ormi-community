package com.community.service;

import com.community.domain.dto.*;
import com.community.domain.entity.Comment;
import com.community.domain.entity.Document;
import com.community.domain.entity.LikeIt;
import com.community.domain.entity.Member;
import com.community.repository.CommentRepository;
import com.community.repository.DocumentRepository;
import com.community.repository.LikeItRepository;
import com.community.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService {

    private CommentRepository commentRepository;
    private DocumentRepository documentRepository;
    private LikeItRepository likeItRepository;
    private MemberRepository memberRepository;

    public CommentService(CommentRepository commentRepository, DocumentRepository documentRepository, LikeItRepository likeItRepository, MemberRepository memberRepository) {
        this.commentRepository = commentRepository;
        this.documentRepository = documentRepository;
        this.likeItRepository = likeItRepository;
        this.memberRepository = memberRepository;
    }

    public CommentResponse findOneById(String commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("존재하지 않는 댓글 입니다."));
        return new CommentResponse(comment);
    }

    public List<CommentResponse> findAllByDocs(String docId) {
        Document document = documentRepository.findById(docId).orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));

        List<Comment> list = commentRepository.findAllByDocument(document);

        if(list.isEmpty()) {
            throw new RuntimeException("댓글이 존재하지 않습니다.");
        }

        return list.stream().map(x -> new CommentResponse(x)).collect(Collectors.toList());
    }

    @Transactional
    public CommentCommonResponse saveComment(String email,String docId, AddCommentRequest request) {
        //document ID 추가
        Document document = documentRepository.findById(docId).orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));
        //UUID 생성
        String uuid = UUID.randomUUID().toString();
        //멤버 ID 추가
        Member member = memberRepository.findByEmail(email).orElseThrow();


        Comment comment = Comment.builder()
                .commentId(uuid)
                .commentPassword(request.getCommentPassword())
                .commentCreatorIp(request.getCommentCreatorIp())
                .commentContent(request.getCommentContent())
                .document(document)
                .commentCreator(member)
                .likeIt(likeItRepository.save(new LikeIt(UUID.randomUUID().toString(), 0L)))
                .build();


        return new CommentCommonResponse(commentRepository.save(comment));
    }

    @Transactional
    public CommentCommonResponse updateComment(String email, String comment_id, ModifyCommentRequest request) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(() -> new RuntimeException("해당 댓글이 존재하지 않습니다."));
        Member member = memberRepository.findByEmail(email).orElseThrow();

        if (email != null && comment.getCommentCreator().getEmail() != null) {
            if (!(comment.getCommentCreator().getMemberId().equals(member.getMemberId()))) throw new RuntimeException("작성자 외에 수정할 수 없습니다.");
        }

        if(comment.getCommentPassword() != null) {
            if (comment.getCommentPassword().equals(request.getCommentPassword()))
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        if(!(request.getCommentContent() == null || comment.getCommentContent().equals(request.getCommentContent()))) {
            comment.setCommentContent(request.getCommentContent());
        }

        comment.setCommentModifier(member);

        return new CommentCommonResponse(comment);
    }

    @Transactional
    public CommentCommonResponse deleteComment(String email, String commentId, DeleteCommentRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("해당 댓글이 존재하지 않습니다."));
        Member member = memberRepository.findByEmail(email).orElseThrow();

        if (email != null && comment.getCommentCreator().getEmail() != null) {
            if (!(comment.getCommentCreator().getMemberId().equals(member.getMemberId()))) throw new RuntimeException("작성자 외에 삭제할 수 없습니다.");
        }

        if(comment.getCommentPassword() != null) {
            if (comment.getCommentPassword().equals(request.getCommentPassword()))
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        commentRepository.delete(comment);
        likeItRepository.delete(comment.getLikeIt());

        return new CommentCommonResponse(comment);
    }


    @Transactional
    public CommentCommonResponse increaseCommentLike(String commentId) {
        Comment comment = commentRepository.getReferenceById(commentId);
        Long count = comment.getLikeIt().getLikeCount() + 1L;
        String likeId = comment.getLikeIt().getLikeId();

        likeItRepository.updateLikeCount(count, likeId);
        return new CommentCommonResponse(comment);
    }
}
