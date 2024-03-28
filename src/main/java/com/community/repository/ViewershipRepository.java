package com.community.repository;

import com.community.domain.entity.Viewership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewershipRepository extends JpaRepository<Viewership, String> {
}
