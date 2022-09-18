package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/**").permitAll().and().csrf().ignoringAntMatchers("/h2-console/**") // 모든 인증되지 않은 요청을 허락
                .and().headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
        // H2는 CSRF 토큰을 발행하는 기능이 없음. 따라서 H2에 대해서만 CSRF 처리를 하지 않도록 예외처리 함
        // H2는 frame 구조로 작성되었고, 스프링 시큐리티는 사이트의 콘텐츠가 다른 사이트에 포함되지 않도록 한다.
        // 따라서 오류가 발생하고 X-Frame-Options 헤더값을 sameorigin으로 설정하여 오류를 해결
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
