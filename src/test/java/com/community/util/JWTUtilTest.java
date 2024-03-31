package com.community.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
@Slf4j
@SpringBootTest
class JWTUtilTest {

    @Autowired
    private JWTUtil jwtUtil;

    @Test
    public void testGenerate() {

        Map<String, Object> claimMap = Map.of("id", "ABCDE");

        String jwtStr = jwtUtil.generateToken(claimMap, 1);

        log.info(jwtStr);
    }

    @Test
    public void testValidate() {

        //유효시간이 지난 토큰 확인
        String jwtStr = "eyJ0eXAiOiJKV1QiLCJpZCI6IkFCQ0RFIiwiYWxnIjoiSFMyNTYifQ.eyJpYXQiOjE3MTE3MTI4MTksImV4cCI6MTcxMTc5OTIxOX0.WRHJp5KKAwpIRhz3GqmJLm5jATv6pjfCDgMzIN53TDA";


        Map<String, Object> claim = jwtUtil.validateToken(jwtStr);
        log.info(claim.toString());
    }


    @Test
    public void GenerateAndValidate() {

        String jwtStr = jwtUtil.generateToken(Map.of("id", "AAAA", "email", "test@test.com"), 1);

        Map<String, Object> claim = jwtUtil.validateToken(jwtStr);

        log.info("ID : " + claim.get("id"));
        log.info("EMAIL : " + claim.get("email"));

    }
}