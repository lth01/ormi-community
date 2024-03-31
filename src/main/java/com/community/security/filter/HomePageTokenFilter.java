package com.community.security.filter;

import com.community.util.JWTUtil;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class HomePageTokenFilter extends OncePerRequestFilter {

    private JWTUtil jwtUtil;

    public HomePageTokenFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        if("/".equals(request.getRequestURI())) {
            String authHeader = request.getHeader("Authorization");
            Map<String, String> tokens = parseRequestJSON(request);
            if (authHeader == null || !authHeader.startsWith("Bearer") || tokens.get("accessToken") == null) {
                Map<String, Object> claim = Map.of("IP", request.getRemoteAddr());

                //Access Token 유효기간 1일
                String accessToken = jwtUtil.generateToken(claim, 1);
                //Refresh Token 유효기간 일주일
                String refreshToken = jwtUtil.generateToken(claim, 7);

                Gson gson = new Gson();

                Map<String, String> keyMap = Map.of("accessToken", accessToken, "refreshToken", refreshToken);

                String jsonStr = gson.toJson(keyMap);

                response.getWriter().println(jsonStr);

            }
        }
        filterChain.doFilter(request, response);
    }

    private Map<String, String> parseRequestJSON(HttpServletRequest request) {

        try(Reader reader = new InputStreamReader(request.getInputStream())) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Map.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
