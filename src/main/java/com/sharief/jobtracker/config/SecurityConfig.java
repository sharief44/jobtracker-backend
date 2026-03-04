package com.sharief.jobtracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sharief.jobtracker.security.JwtAuthFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig { 
	
	 private final JwtAuthFilter jwtAuthFilter;
	 
	 public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
	        this.jwtAuthFilter = jwtAuthFilter;
	    }

	 @Bean
	 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	     http
	         .cors(cors -> {}) // 🔥 enable cors
	         .csrf(csrf -> csrf.disable())
	         .authorizeHttpRequests(auth -> auth
	                 .requestMatchers("/api/users/register", "/api/users/login")
	                 .permitAll()
	                 .anyRequest().authenticated()
	         )
	         .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

	     return http.build();
	 }
    
    @Bean
    public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}