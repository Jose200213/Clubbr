package com.Clubbr.Clubbr.config.security;

import com.Clubbr.Clubbr.utils.permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import com.Clubbr.Clubbr.config.security.filter.jwtAuthenticationFilter;

@Component
@EnableWebSecurity
public class httpSecurityConfig {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private jwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf( csrfConfig -> csrfConfig.disable())
                .sessionManagement( sessionManagementConfig -> sessionManagementConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(buildersRequestMatchers());

        return httpSecurity.build();
    }

    private static Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> buildersRequestMatchers() {
        return authConfig -> {
            //region AUTHENTICATION CONTROLLER
            authConfig.requestMatchers(HttpMethod.POST, "/authentication/login").permitAll();
            authConfig.requestMatchers(HttpMethod.POST, "/authentication/register").permitAll();
            authConfig.requestMatchers("/error").permitAll();
            //endregion
            //region STABLISHMENT CONTROLLER
            authConfig.requestMatchers(HttpMethod.GET, "/stablishment/all").hasAuthority(permission.READ_STABLISHMENTS.name());
            authConfig.requestMatchers(HttpMethod.GET, "/stablishment/{stablishmentID}").hasAuthority(permission.READ_STABLISHMENTS.name());
            authConfig.requestMatchers(HttpMethod.GET, "/stablishment/manager/all").hasAuthority(permission.READ_MANAGER_STABLISHMENTS.name());

            authConfig.requestMatchers(HttpMethod.POST, "/stablishment/add").hasAuthority(permission.CREATE_STABLISHMENTS.name());
            authConfig.requestMatchers(HttpMethod.POST, "/stablishment/{stablishmentID}/worker/add").hasAuthority(permission.CREATE_WORKERS.name());
            authConfig.requestMatchers(HttpMethod.POST, "/stablishment/{stablishmentID}/manager/{userID}/add").hasAuthority(permission.CREATE_STAB_MANAGERS.name());
            authConfig.requestMatchers(HttpMethod.POST, "/stablishment/{stablishmentID}/manager/{userID}/addOwner").hasAuthority(permission.CREATE_STAB_OWNER.name());

            authConfig.requestMatchers(HttpMethod.PUT, "/stablishment/{stablishmentID}/event/{eventName}/worker/{userID}/interestPoint/{interestPointID}/update").hasAuthority(permission.UPDATE_WORKERS.name());
            authConfig.requestMatchers(HttpMethod.PUT, "/stablishment/{stablishmentID}/worker/{userID}/interestPoint/{interestPointID}/update").hasAuthority(permission.UPDATE_WORKERS.name());
            authConfig.requestMatchers(HttpMethod.PUT, "/stablishment/update").hasAuthority(permission.UPDATE_STABLISHMENTS.name());

            authConfig.requestMatchers(HttpMethod.DELETE, "/stablishment/delete/{stablishmentID}").hasAuthority(permission.DELETE_STABLISHMENTS.name());
            authConfig.requestMatchers(HttpMethod.DELETE, "/stablishment/{stablishmentID}/worker/{userID}").hasAuthority(permission.DELETE_STABLISHMENT_WORKERS.name());
            //endregion
            //region EVENT CONTROLLER
            authConfig.requestMatchers(HttpMethod.GET, "/stablishment/{stablishmentID}/event/allordered").hasAuthority(permission.READ_EVENTS.name());
            authConfig.requestMatchers(HttpMethod.GET, "/stablishment/{stablishmentID}/event/{eventName}/{eventDate}").hasAuthority(permission.READ_EVENTS.name());

            authConfig.requestMatchers(HttpMethod.POST, "/stablishment/{stablishmentID}/event/add").hasAuthority(permission.CREATE_EVENTS.name());

            authConfig.requestMatchers(HttpMethod.PUT, "/stablishment/{stablishmentID}/event/update/{eventName}/{eventDate}").hasAuthority(permission.UPDATE_EVENTS.name());

            authConfig.requestMatchers(HttpMethod.DELETE, "/stablishment/{stablishmentID}/event/delete/{eventName}/{eventDate}").hasAuthority(permission.DELETE_EVENTS.name());


            //endregion
            //region INTERESTPOINT CONTROLLER
            authConfig.requestMatchers(HttpMethod.GET, "/stablishment/{stablishmentID}/interestPoint/{interestPointID}").hasAuthority(permission.READ_INTEREST_POINTS.name());
            authConfig.requestMatchers(HttpMethod.GET, "/stablishment/{stablishmentID}/interestPoint/all").hasAuthority(permission.READ_INTEREST_POINTS.name());
            authConfig.requestMatchers(HttpMethod.GET, "/stablishment/{stablishmentID}/event/{eventName}/interestPoint/{interestPointID}").hasAuthority(permission.READ_INTEREST_POINTS.name());
            authConfig.requestMatchers(HttpMethod.GET, "/stablishment/{stablishmentID}/event/{eventName}/interestPoint/all").hasAuthority(permission.READ_INTEREST_POINTS.name());

            authConfig.requestMatchers(HttpMethod.POST, "/stablishment/{stablishmentID}/interestPoint/add").hasAuthority(permission.CREATE_INTEREST_POINTS.name());
            authConfig.requestMatchers(HttpMethod.POST, "/stablishment/{stablishmentID}/event/{eventName}/interestPoint/add").hasAuthority(permission.CREATE_INTEREST_POINTS.name());

            authConfig.requestMatchers(HttpMethod.PUT, "/stablishment/{stablishmentID}/interestPoint/{interestPointID}").hasAuthority(permission.UPDATE_INTEREST_POINTS.name());
            authConfig.requestMatchers(HttpMethod.PUT, "/stablishment/{stablishmentID}/event/{eventName}/interestPoint/{interestPointID}").hasAuthority(permission.UPDATE_INTEREST_POINTS.name());

            authConfig.requestMatchers(HttpMethod.DELETE, "/stablishment/{stablishmentID}/interestPoint/delete/{interestPointID}").hasAuthority(permission.DELETE_INTEREST_POINTS.name());
            authConfig.requestMatchers(HttpMethod.DELETE, "/stablishment/{stablishmentID}/event/{eventName}/interestPoint/delete/{interestPointID}").hasAuthority(permission.DELETE_INTEREST_POINTS.name());
            //endregion
            //region ITEM CONTROLLER
            authConfig.requestMatchers(HttpMethod.GET, "/stablishment/{stablishmentID}/item/all").hasAuthority(permission.READ_ITEMS.name());
            authConfig.requestMatchers(HttpMethod.GET, "/stablishment/{stablishmentID}/item/{itemID}").hasAuthority(permission.READ_ITEMS.name());

            authConfig.requestMatchers(HttpMethod.POST, "/stablishment/{stablishmentID}/item/add").hasAuthority(permission.CREATE_ITEMS.name());

            authConfig.requestMatchers(HttpMethod.PUT, "/stablishment/{stablishmentID}/item/update/{itemID}").hasAuthority(permission.UPDATE_ITEMS.name());

            authConfig.requestMatchers(HttpMethod.DELETE, "/stablishment/{stablishmentID}/item/delete/{itemID}").hasAuthority(permission.DELETE_ITEMS.name());

            //endregion
            //region TICKET CONTROLLER
            authConfig.requestMatchers(HttpMethod.GET, "/ticket/all").hasAuthority(permission.READ_TICKETS.name());
            authConfig.requestMatchers(HttpMethod.GET, "/ticket/{ticketID}").hasAuthority(permission.READ_TICKETS.name());

            authConfig.requestMatchers(HttpMethod.POST, "/stablishment/{stablishmentID}/event/{eventName}/ticket/add").hasAuthority(permission.CREATE_TICKETS.name());
            //endregion
            //region USER CONTROLLER
            authConfig.requestMatchers(HttpMethod.GET, "/user/all").hasAuthority(permission.READ_USERS.name());
            authConfig.requestMatchers(HttpMethod.GET, "/user/{userID}").hasAuthority(permission.READ_MY_USER.name());

            authConfig.requestMatchers(HttpMethod.POST, "/user/manager/{userID}").hasAuthority(permission.CREATE_OWNER_MANAGER.name());

            authConfig.requestMatchers(HttpMethod.PUT, "/user/update").hasAuthority(permission.UPDATE_USERS.name());

            authConfig.requestMatchers(HttpMethod.DELETE, "/user/{userID}").hasAuthority(permission.DELETE_USERS.name());
            //endregion
            //region WORKER CONTROLLER
            authConfig.requestMatchers(HttpMethod.GET, "/stablishment/{stablishmentID}/worker/all").hasAuthority(permission.READ_STAB_WORKERS.name());
            authConfig.requestMatchers(HttpMethod.GET, "/stablishment/{stablishmentID}/worker/{userID}").hasAuthority(permission.READ_WORKERS.name());

            authConfig.requestMatchers(HttpMethod.PUT, "/stablishment/{stablishmentID}/worker/update").hasAuthority(permission.UPDATE_WORKERS.name());
            //endregion

            authConfig.anyRequest().denyAll();

            //authConfig.anyRequest().permitAll();
        };
    }
}
