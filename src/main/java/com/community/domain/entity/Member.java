package com.community.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
public class Member {

    @Id
    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "email",nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "gender")
    private String gender;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "find_password_answer", nullable = false)
    private String findPasswordAnswer;

    @Column(name = "withdrawal")
    private Boolean withdrawal;

    @CreatedDate
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "mod_date")
    private LocalDateTime modDate;

    //relation with question
    @ManyToOne
    @JoinColumn(name = "password_question_id")
    private PasswordQuestion passwordQuestion;

    //relation with authority
    @ManyToOne
    @JoinColumn(name = "authority_id")
    private Authorities authorities;
}
