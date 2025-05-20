package com.DariM.darim.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
// import com.miniproject.demo.models.User;

// import com.miniproject.demo.jwtModule.models.AppUser;
// import com.miniproject.demo.jwtModule.repositories.UserRepository;
// import com.miniproject.demo.jwtModule.repositories.UserRepository;
import com.DariM.darim.repository.UserRepository;


@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    public MyUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.DariM.darim.model.User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}



