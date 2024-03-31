package com.community.repository;

import com.community.domain.entity.LikeIt;
import com.community.domain.entity.LikeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeListRepository extends JpaRepository<LikeList, String> {
    List<LikeList> findAllByUserIp(String userIp);
}
