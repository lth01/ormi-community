package com.community.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteCommentRequest {
    private String commentPassword;
}
