package com.community.domain.dto;

import com.community.domain.entity.Member;
import com.community.domain.entity.PasswordQuestion;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberResponse {
    private String nickname;
    private String email;

}
