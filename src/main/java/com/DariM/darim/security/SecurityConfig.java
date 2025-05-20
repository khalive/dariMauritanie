// package com.DariM.darim.security;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// import com.DariM.darim.service.MyUserDetailsService;
// import com.DariM.darim.utils.JwtFilter;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     @Autowired private JwtFilter jwtFilter;
//     @Autowired private MyUserDetailsService userDetailsService;

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         return http
//                 .csrf(csrf -> csrf.disable())
//                 .authorizeHttpRequests(auth -> auth
//                         .requestMatchers("/login","/register","/forgot-password","/reset-password","/","/css/**","/js/**").permitAll()
//                         .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
//                         //.requestMatchers("/auth/**", "/students/**", "/products/**").permitAll()  // 👈 public routes  
//                         .anyRequest().authenticated()
//                 )
//                 .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                 .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                 .userDetailsService(userDetailsService)
//                 .build();
//     }

//     @Bean
//     public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
//         return config.getAuthenticationManager();
//     }

//     // @Bean
//     // public PasswordEncoder passwordEncoder() {
//     //     return new BCryptPasswordEncoder();
//     // }
// }]

package com.DariM.darim.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.DariM.darim.service.MyUserDetailsService;
import com.DariM.darim.utils.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired 
    private JwtFilter jwtFilter;
    
    @Autowired 
    private MyUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                            // "/auth/**",
                            "/login",
                            "/api/auth/register",
                            "/api/auth/",
                            "/api/auth/login",
                            "/api/auth/forgot-password",
                            "/api/auth/reset-password",
                            
                             
                            "/register",
                            "/forgot-password",
                            "/reset-password",
                            "/",
                            "/css/**",
                            "/js/**",
                            "/v3/api-docs/**", 
                            "/swagger-ui/**", 
                            "/swagger-ui.html"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailsService)
                .build();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    
}
