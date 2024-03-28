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
public class Industry {

    @Id
    @Column(name = "industry_id")
    private String industryId;

    @Column(name = "industry_name")
    private String industryName;

    @Column(name = "industry_description")
    private String industryDescription;
}
