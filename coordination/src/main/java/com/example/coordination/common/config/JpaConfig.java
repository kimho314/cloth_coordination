package com.example.coordination.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.example.coordination.domain.repository")
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}
