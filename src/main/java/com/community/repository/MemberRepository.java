package com.community.repository;

import com.community.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    public Optional<Member> findByEmail(String email);

    public boolean existsByEmail(String email);

    public boolean existsByNickname(String Nickname);

    @Modifying
    @Query("UPDATE Member SET withdrawal = :withdrawal WHERE email = :email")
    void updateWithdrawal(boolean withdrawal, String email);
}
