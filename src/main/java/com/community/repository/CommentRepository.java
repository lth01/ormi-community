package com.community.repository;

import com.community.domain.entity.Comment;
import com.community.domain.entity.Document;
import com.community.domain.entity.Member;
import com.community.domain.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    List<Comment> findAllByDocument(Document document);
    List<Comment> findAllByCommentCreator(Member member);
    void deleteAllByDocument(Document document);
    Optional<Comment> findByReport(Report report);

}
