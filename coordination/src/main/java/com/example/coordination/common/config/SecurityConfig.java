package com.example.coordination.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .headers(header -> header.frameOptions(frameOption -> frameOption.disable()))
                .authorizeHttpRequests(request -> request.requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/client/**").hasRole("CLIENT")
                        .requestMatchers("/admin/**").hasRole("ADMIn")
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> basic.disable())
        ;

        return http.build();
    }
}
