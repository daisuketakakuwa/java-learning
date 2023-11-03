package jwt.auth.jwtauth.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jwt.auth.jwtauth.filter.JwtFilter;

@Configuration
public class ApplicationConfig {
    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        JwtFilter jwtFilter = new JwtFilter();
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtFilter);
        registrationBean.addUrlPatterns("/hello/*");

        return registrationBean;
    }
}
