package com.community.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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
