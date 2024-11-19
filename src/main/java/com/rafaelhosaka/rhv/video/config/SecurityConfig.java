package com.rafaelhosaka.rhv.video.config;

import com.rafaelhosaka.rhv.video.utils.HmacUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${HEADER_VALUE}")
    private String value;

    @Value("${HEADER_SECRET}")
    private String secret;

    @PostConstruct
    public void validate() {
        if (value == null || value.isEmpty()) {
            throw new IllegalStateException("HEADER_VALUE must be set in the environment");
        }
        if (secret == null || secret.isEmpty()) {
            throw new IllegalStateException("HEADER_SECRET must be set in the environment");
        }
    }

    @Bean
    public RequestMatcher matcher() {
        return request -> {
            try {
                return  isRequestFromTrustedService(request);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    private boolean isRequestFromTrustedService(HttpServletRequest request) throws Exception {
        String header = request.getHeader("RHV-Header");
        return header != null && HmacUtils.verifyHmacSignature(value, secret, header);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, RequestMatcher matcher) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers(matcher).permitAll());
        return http.build();
    }
}
