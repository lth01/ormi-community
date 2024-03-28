package com.community.repository;

import com.community.domain.entity.LikeIt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeItRepository extends JpaRepository<LikeIt, String> {
}
