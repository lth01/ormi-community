package com.community.repository;

import com.community.domain.entity.Document;
import com.community.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {
    List<Document> findAllByDocCreator(Member member);
}
