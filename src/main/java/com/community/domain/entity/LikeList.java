package com.community.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "like_list")
@EntityListeners(value = AuditingEntityListener.class)
public class LikeList {

    @Id
    @Column(name = "Like_id")
    private String likeId;

    @Column(name = "user_ip", nullable = false)
    private String userIp;

    @Column(name = "doc_com_name")
    private String docComName;

    @CreatedDate
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;
}
