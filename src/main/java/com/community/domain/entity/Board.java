package com.community.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Board {

    @Id
    @Column(name = "board_id")
    private String boardId;

    @CreatedDate
    @Column(name = "board_create_date")
    private LocalDateTime boardCreateDate;

    //relation with industry
    @ManyToOne
    @JoinColumn(name = "industry_id")
    private Industry industry;

    //relation with companies
    @ManyToOne
    @JoinColumn(name = "com_id", nullable = false)
    private Companies companies;

    //relation with admin
    @ManyToOne
    @JoinColumn(name = "board_creator")
    private Member memberAdmin;

    //relation with user
    @ManyToOne
    @JoinColumn(name = "board_requester", nullable = false)
    private Member memberUser;

    @Column(name = "commu_board_request_approve")
    private Boolean approve;
}
