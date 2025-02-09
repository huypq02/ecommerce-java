package com.cybersoft.ecommerce.config;

import com.cybersoft.ecommerce.filter.CustomSecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomSecurityFilter filter, CorsConfigurationSource corsConfigurationSource)throws Exception{
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(ss -> ss.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> {
                    // giúp định nghĩa quyền truy cập cho các link
                    request.requestMatchers("/login", "/register", "/download/**").permitAll();
                    request.requestMatchers("/user/auth/social", "/user/auth/social/callback").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/product").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/category").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/collection").permitAll();


                    request.requestMatchers(HttpMethod.POST, "/product").hasRole("ADMIN");
                    request.requestMatchers(HttpMethod.PUT, "/product").hasRole("ADMIN");
                    request.requestMatchers(HttpMethod.DELETE, "/product").hasRole("ADMIN");

                    request.requestMatchers(HttpMethod.POST, "/category").hasRole("ADMIN");
                    request.requestMatchers(HttpMethod.PUT, "/category").hasRole("ADMIN");
                    request.requestMatchers(HttpMethod.DELETE, "/category").hasRole("ADMIN");

                    request.requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN");
                    request.requestMatchers(HttpMethod.DELETE, "/users").hasRole("ADMIN");

                    request.anyRequest().authenticated();
                })
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5500", "http://localhost:3000"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
