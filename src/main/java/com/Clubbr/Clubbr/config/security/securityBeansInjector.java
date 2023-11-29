package com.Clubbr.Clubbr.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import com.Clubbr.Clubbr.Repository.userRepo;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class securityBeansInjector {

    @Autowired
    private userRepo userRepo;
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfig) throws Exception {
        return authenticationConfig.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userID -> {
            return userRepo.findById(userID)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        };
    }
}
