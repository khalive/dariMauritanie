package com.DariM.darim.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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
import com.DariM.darim.model.User;
import com.DariM.darim.repository.UserRepository;
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
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/auth/**",
                    "/v3/api-docs/**", 
                    "/swagger-ui/**", 
                    "/swagger-ui.html"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .httpBasic(httpBasic -> httpBasic.realmName("DariM API")) // Activation de Basic Auth
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    CommandLineRunner initUsers(UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            if (userRepo.findByEmail("23091@supnum.mr").isEmpty()) {
                User user = new User();
                user.setName("Utilisateur Test");
                user.setEmail("23091@supnum.mr");
                user.setPassword(encoder.encode("test2"));
                user.setRole(User.Role.USER);
                userRepo.save(user);
                System.out.println("Utilisateur USER créé : 23091@supnum.mr");
            }

            if (userRepo.findByEmail("23090@supnum.mr").isEmpty()) {
                User admin = new User();
                admin.setName("Administrateur Test");
                admin.setEmail("23090@supnum.mr");
                admin.setPassword(encoder.encode("test1"));
                admin.setRole(User.Role.ADMIN);
                userRepo.save(admin);
                System.out.println("Utilisateur ADMIN créé : 23090@supnum.mr");
            }
        };
    }
}