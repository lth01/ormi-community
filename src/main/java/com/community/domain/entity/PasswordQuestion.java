package com.community.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "password_question")
public class PasswordQuestion {

    @Id
    @Column(name = "password_question_id")
    private String passwordQuestionId;

    @Column(name = "question", nullable = false)
    private String question;

}
