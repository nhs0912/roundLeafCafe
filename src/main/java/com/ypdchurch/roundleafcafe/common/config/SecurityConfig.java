package com.ypdchurch.roundleafcafe.common.config;

import com.ypdchurch.roundleafcafe.common.util.CustomResponseUtil;
import com.ypdchurch.roundleafcafe.member.enums.MemberRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Slf4j
@Configuration
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors((cors) -> cors.configurationSource(configurationSource()))
                .httpBasic(AbstractHttpConfigurer::disable) //로그인 인증창 해제
                .formLogin(AbstractHttpConfigurer::disable) //formLogin 해제
                .headers(header -> header.frameOptions((HeadersConfigurer.FrameOptionsConfig::disable))) //iframe 해제

                .authorizeHttpRequests(request -> {
                    request.requestMatchers(PathRequest.toH2Console()).permitAll();
                    request.requestMatchers(antMatcher("/")).permitAll();
                    request.requestMatchers(antMatcher("/login")).permitAll();
                    request.requestMatchers(antMatcher("/api/member/**")).permitAll();
                    request.requestMatchers(antMatcher("/api/order/**")).permitAll();
                    request.requestMatchers(antMatcher("/admin")).hasRole(MemberRole.ADMIN.name());
                    request.requestMatchers(antMatcher("/api/customer/**")).hasRole(MemberRole.CUSTOMER.name());

                    request.requestMatchers(antMatcher("/api/manager/**")).hasRole(MemberRole.MANAGER.name());
                    request.requestMatchers(antMatcher("/api/staff/**")).hasRole(MemberRole.STAFF.name())
                            .anyRequest().authenticated();
                })
                .exceptionHandling(custom -> custom.authenticationEntryPoint((request, response, authException) -> {
                    log.error("heeseok response = {}", response);
                    CustomResponseUtil.unAuthentication(response, "로그인을 해야합니다.");
                }))

                //jSessionId 사용 거부
                .sessionManagement(sessionManegement
                        -> sessionManegement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (Javascript 요청 허용)
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (프론트 앤드 IP만 허용 react)
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader("Authorization"); // 옛날에는 디폴트 였다. 지금은 아닙니다.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
