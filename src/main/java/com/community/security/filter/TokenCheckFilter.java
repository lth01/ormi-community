package com.community.security.filter;

import com.community.security.exception.AccessTokenException;
import com.community.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

/**
 * TokenCheckFilter
 * 로그인한 사용자인지 체크하는 로그인 체크용 필터와 유사하게 JWT 토큰을 검사하는 역할을 위해 사용
 *
 * spring의 OncePerRequestFilter를 상속, 하나의 요청에 대해 한번씩 동작하는 필터이다.
 */
@Slf4j
public class TokenCheckFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public TokenCheckFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        if (!path.startsWith("/like")) {
            filterChain.doFilter(request,response);
            return;
        }

        log.info("Token check filter.....................");
        log.info("JWTUtil : " + jwtUtil);

        try {
            //토큰 검사 이후 다음 필터로 전송
            validateAccessToken(request);
            filterChain.doFilter(request,response);
        } catch (AccessTokenException accessTokenException) {
            accessTokenException.sendResponseError(response);
        }
    }

    /**
     * 토큰이 Bearer 형식인지 확인하는 메서드
     * 토큰은 해당 타입(Bearer)과 인증값을 "type+인증값"으로 보내기 때문에
     * 1. 토큰의 이상여부 확인
     * 2. 타입 확인
     * 3. 토큰의 만료 및 유효성 검사
     */
    private Map<String, Object> validateAccessToken(HttpServletRequest request) throws AccessTokenException {
        String headerStr = request.getHeader("Authorization");

        if (headerStr == null || headerStr.length() < 8) {
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.UNACCEPT);
        }

        // Bearer 검사
        String tokenType = headerStr.substring(0, 6);
        String tokenStr = headerStr.substring(7);

        if (!"Bearer".equalsIgnoreCase(tokenType)) {
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADTYPE);
        }

        try {
            Map<String, Object> values = jwtUtil.validateToken(tokenStr);
            return values;
        } catch (MalformedJwtException malformedJwtException) {
            log.error("MalformedJwtException---------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.MALFORM);
        } catch (SignatureException signatureException) {
            log.error("SignatureException---------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADSIGN);
        } catch (ExpiredJwtException expiredJwtException) {
            log.error("ExpiredJwtException---------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.EXPIRED);
        }
    }
}
