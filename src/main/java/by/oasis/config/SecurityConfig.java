package by.oasis.config;

import by.oasis.controller.filters.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter filter) throws Exception  {

        http = http.cors().and().csrf().disable();

        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
                )
                .accessDeniedHandler((request, response, ex) -> response.setStatus(HttpServletResponse.SC_FORBIDDEN))
                .and();

        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/users/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/security/token/verification").authenticated()
                .requestMatchers("/cabinet/registration").permitAll()
                .requestMatchers("/cabinet/verification").permitAll()
                .requestMatchers("/cabinet/login").permitAll()
            .requestMatchers("/cabinet/pre-password-reset").permitAll()
            .requestMatchers("/cabinet/password-reset/{uuid}").permitAll()
                .requestMatchers("/cabinet/logout").authenticated()
                .requestMatchers("/cabinet/change-password").authenticated()
                .requestMatchers("/cabinet/pre-change-password").authenticated()
                .requestMatchers("/cabinet/me").authenticated()
                .requestMatchers("/cabinet/delete-me-account").authenticated()
                .requestMatchers("/cabinet/pre-delete-me-account").authenticated()
            .requestMatchers("/pre-password-reset").permitAll()
                .anyRequest().authenticated()
        );

        http.addFilterBefore(
                filter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}
