package com.community.domain.dto;

import com.community.domain.entity.Member;
import com.community.domain.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private String nickname;
    private String email;
    private String memberRoleName;

    public UserInfoResponse(Member member) {
        nickname = member.getNickname();
        email = member.getEmail();
        memberRoleName = member.getMemberRole().getMemberRoleName();
    }
}
