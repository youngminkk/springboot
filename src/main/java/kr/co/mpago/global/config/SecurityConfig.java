package kr.co.mpago.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.mpago.domain.user.Role;
import kr.co.mpago.domain.user.repository.UserRepository;
import kr.co.mpago.global.config.jwt.JwtAuthenticationEntryPoint;
import kr.co.mpago.global.config.jwt.filter.JwtAuthenticationProcessingFilter;
import kr.co.mpago.global.config.jwt.filter.JwtExceptionFilter;
import kr.co.mpago.global.config.jwt.service.JwtService;
import kr.co.mpago.global.login.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import kr.co.mpago.global.login.handler.LoginFailureHandler;
import kr.co.mpago.global.login.handler.LoginSuccessHandler;
import kr.co.mpago.global.login.service.LoginService;
import kr.co.mpago.global.oauth2.handler.OAuth2LoginFailureHandler;
import kr.co.mpago.global.oauth2.handler.OAuth2LoginSuccessHandler;
import kr.co.mpago.global.oauth2.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final LoginService loginService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtAuthenticationEntryPoint entryPoint;
    private final JwtExceptionFilter jwtExceptionFilter;

    private static final List<RequestMatcher> specialUrlMatchers = Arrays.asList(
            //모든 사용자 접근
            new AntPathRequestMatcher("/api/v1/account/login/**"),
            new AntPathRequestMatcher("/api/v1/account/signup"),
            new AntPathRequestMatcher("/api/v1/user/profile"),
            new AntPathRequestMatcher("/api/v1/account/custom-logout"),
            new AntPathRequestMatcher("/oauth2/**"),
            new AntPathRequestMatcher("/api/v1/user/state/**"),
            new AntPathRequestMatcher("/api/v1/email/password-find/**"),
            new AntPathRequestMatcher("/api/v1/search/**")
    );
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
                    .requestMatchers(specialUrlMatchers.toArray(new RequestMatcher[0])).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/api/v1/**")).hasRole(Role.USER.name())
                    .requestMatchers(new AntPathRequestMatcher("/api/admin/**")).hasRole(Role.ADMIN.name())
                    .requestMatchers(new AntPathRequestMatcher("/api/user/**")).hasRole(Role.USER.name())
                    .anyRequest().authenticated())

            .oauth2Login(oauth2Login -> oauth2Login
                    .successHandler(oAuth2LoginSuccessHandler)
                    .failureHandler(oAuth2LoginFailureHandler)
                    .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(customOAuth2UserService))
            );


        http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionFilter, jwtAuthenticationProcessingFilter().getClass());
        http.addFilterBefore(jwtAuthenticationProcessingFilter(), BasicAuthenticationFilter.class)
                .exceptionHandling(handler -> handler.authenticationEntryPoint(entryPoint))
        ;

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
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        return new JwtAuthenticationProcessingFilter(jwtService, userRepository,specialUrlMatchers);
    }

    @Bean
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordLoginFilter
                = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);
        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return customJsonUsernamePasswordLoginFilter;
    }

    /**
     * UserDetailsService는 커스텀 LoginService로 등록
     * 또한, FormLogin과 동일하게 AuthenticationManager로는 ProviderManager 사용하고 구현체로는 DaoAuthenticationProvider
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(loginService);
        return new ProviderManager(provider);
    }

    /**
     * 로그인 성공 시 호출되는 LoginSuccessJWTProviderHandler 빈 등록
     */
    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtService, userRepository);
    }

    /**
     * 로그인 실패 시 호출되는 LoginFailureHandler 빈 등록
     */
    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }
}

