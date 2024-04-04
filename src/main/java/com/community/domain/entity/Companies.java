package com.community.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Companies {

    @Id
    @Column(name = "com_id")
    private String comId;

    @Column(name = "reg_num")
    private String regNum;

    @Column(name = "com_name")
    private String comName;
}
