package com.community.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class IndustryResponse {
    //프론트에서 Select 컴포넌트에 사용하는 데이터는 value, title object로 표현됨
    private String value;
    private String title;
}
