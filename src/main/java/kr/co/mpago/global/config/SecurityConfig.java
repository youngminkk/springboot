package kr.co.mpago.global.config;

import kr.co.mpago.domain.user.Role;
import kr.co.mpago.global.auth.handler.OAuth2AuthenticationFailureHandler;
import kr.co.mpago.global.auth.handler.OAuth2AuthenticationSuccessHandler;
import kr.co.mpago.global.auth.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import kr.co.mpago.global.auth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    http.
            // 1. CSRF 해제
            csrf(AbstractHttpConfigurer::disable)
            // 2. iframe 거부
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            // 3. cors 재설정
            .cors(cors -> cors.configurationSource(configurationSource()))
            // 4. jSessionId 사용 거부
            .sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // 5. form 로긴 해제
            .formLogin(AbstractHttpConfigurer::disable)
            // 6. 로그인 인증창이 뜨지 않게 비활성화
            .httpBasic(AbstractHttpConfigurer::disable)
            //인증, 권한 필터 설정
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            new AntPathRequestMatcher("/"),
                            new AntPathRequestMatcher("/profile"),
                            new AntPathRequestMatcher("/login")
                    ).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/api/v1/**")).hasRole(Role.USER.name())
                    .requestMatchers(antMatcher("/api/admin/**")).hasRole(Role.ADMIN.name())
                    .requestMatchers(antMatcher("/api/user/**")).hasRole(Role.USER.name())
                    .anyRequest().authenticated()
            )
            .logout(logout ->
                    logout.logoutSuccessUrl("/"));

        return http.build();
    }
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (프론트 앤드 IP만 허용 react)
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
