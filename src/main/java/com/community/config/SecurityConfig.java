package com.community.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
                //Swagger UI
                .authorizeHttpRequests(auth -> auth.requestMatchers("/v3/**","/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll())
                //CSRF 비활성화
                .csrf(csrf -> csrf.disable());
                //세션 비활성화
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //test를 위한 모든 사용자 권한
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
                // 익명 사용자에게 부여될 권한 // 익명 사용자의 principal 이름 정의 (기본값은 'anonymousUser')
//            http.anonymous( any -> any.authorities("ROLE_ANONYMOUS").principal("anonymousUser"));

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        log.info("--------------------------web configure----------------------------");
        return (web) -> web.ignoring().requestMatchers("/v3/**","/static/**","/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
