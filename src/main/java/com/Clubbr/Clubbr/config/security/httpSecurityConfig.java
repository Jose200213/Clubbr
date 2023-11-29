package com.Clubbr.Clubbr.config.security;

import com.Clubbr.Clubbr.utils.permission;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class httpSecurityConfig {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf( csrfConfig -> csrfConfig.disable())
                .sessionManagement( sessionManagementConfig -> sessionManagementConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests( authConfig -> {
                    authConfig.requestMatchers(HttpMethod.POST, "/authentication/login").permitAll();
                    authConfig.requestMatchers(HttpMethod.GET, "/authentication/public-access").permitAll();
                    authConfig.requestMatchers("/error").permitAll();

                    authConfig.requestMatchers(HttpMethod.POST, "/stablishment/add").hasAuthority(permission.WRITE_ALL.name());
                    authConfig.requestMatchers(HttpMethod.GET, "/stablishment/{stablishmentID}/item/all").hasAuthority(permission.READ_ALL.name());
                    authConfig.anyRequest().denyAll();
                });

        return httpSecurity.build();
    }
}
