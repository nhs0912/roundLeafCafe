package com.ypdchurch.roundleafcafe.config;

import com.ypdchurch.roundleafcafe.enums.MemberRole;
import lombok.extern.slf4j.Slf4j;
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

    // JWT 필터 등록이 필요함
    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
//            builder.addFilter(new MyJwtAuthorizationFilter(authenticationManager));
            // 시큐리티 관련 필터
            super.configure(builder);
        }
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable) //로그인 인증창 해제
                .formLogin(AbstractHttpConfigurer::disable) //formLogin 해제
                .headers(header-> header.frameOptions((HeadersConfigurer.FrameOptionsConfig::disable))) //iframe 해제

                .authorizeHttpRequests(request -> {
                    request.requestMatchers(antMatcher("/login")).permitAll();
                            request.requestMatchers(antMatcher("/admin")).hasRole(MemberRole.ADMIN.name())
                            .anyRequest().authenticated();
                })

                //jSessionId 사용 거부
                .sessionManagement(sessionManegement
                        -> sessionManegement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .apply(new CustomSecurityFilterManager(), UsernamePasswordAuthenticationFilter.class)
                        .build();


//                .apply((custom) -> new CustomSecurityFilterManager())


        // 7. 커스텀 필터 적용 (시큐리티 필터 교환)
//        http.apply(new CustomSecurityFilterManager());
//
//        // 8. 인증 실패 처리
//        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
//            log.warn("인증되지 않은 사용자가 자원에 접근하려 합니다 : "+authException.getMessage());
//            MyFilterResponseUtil.unAuthorized(response, new Exception401("인증되지 않았습니다"));
//        });
//
//        // 10. 권한 실패 처리
//        http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
//            log.warn("권한이 없는 사용자가 자원에 접근하려 합니다 : "+accessDeniedException.getMessage());
//            MyFilterResponseUtil.forbidden(response, new Exception403("권한이 없습니다"));
//        });
//
//        // 11. 인증, 권한 필터 설정
//        http.authorizeRequests(
//                authorize -> authorize.antMatchers("/s/**").authenticated()
//                        .antMatchers("/manager/**")
//                        .access("hasRole('ADMIN') or hasRole('MANAGER')")
//                        .antMatchers("/admin/**").hasRole("ADMIN")
//                        .anyRequest().permitAll()
//        );


    }

//    public CorsConfigurationSource configurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (Javascript 요청 허용)
//        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (프론트 앤드 IP만 허용 react)
//        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
//        configuration.addExposedHeader("Authorization"); // 옛날에는 디폴트 였다. 지금은 아닙니다.
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}
