package com.community.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Authorities implements GrantedAuthority {

    @Id
    @Column(name = "authority_id")
    private String authorityId;

    @Column(name = "authority_name", nullable = false)
    private String authorityName;

    //권한 처리
    @Override
    public String getAuthority() {
        return authorityName;
    }
}
