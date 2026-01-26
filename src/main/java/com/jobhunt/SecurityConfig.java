//package com.jobhunt;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.jobhunt.jwt.JwtAuthenticationEntryPoint;
//import com.jobhunt.jwt.JwtAuthenticationFilter;
//
//@Configuration
//public class SecurityConfig {
//	@Autowired
//	private JwtAuthenticationEntryPoint point;
//	
//	@Autowired
//	private JwtAuthenticationFilter filter;
//	
//	
//	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
////		http.authorizeHttpRequests((req)->req.requestMatchers("/**").permitAll().anyRequest().authenticated());
////		http.csrf(csrf->csrf.disable());
////		return http.build();
//		http.csrf(csrf->csrf.disable())
//		.authorizeRequests()
//		.requestMatchers("/auth/login" , "/users/register" , "/users/verifyOtp/**" , "/users/sendOtp/**")
//		.permitAll()
//		.anyRequest()
//		.authenticated()
//		.and()
//		.exceptionHandling(ex->ex.authenticationEntryPoint(point))
//		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
//		
//		return http.build();
//	}
//}








package com.jobhunt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.jobhunt.jwt.JwtAuthenticationEntryPoint;
import com.jobhunt.jwt.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint point;

    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/auth/login",
                    "/users/register",
                    "/users/verifyOtp/**",
                    "/users/sendOtp/**"
                ).permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // ✅ allow preflight
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
 
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}

