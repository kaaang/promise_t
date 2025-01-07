package kr.co.promise_t.api.config;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class FilterConfig {
    @Bean
    public Filter requestLoggingFilter() {
        return new RequestLoggingFilter();
    }
}
