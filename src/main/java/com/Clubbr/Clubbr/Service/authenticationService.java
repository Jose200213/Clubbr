package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Dto.authenticationRequest;
import com.Clubbr.Clubbr.Dto.authenticationResponse;
import com.Clubbr.Clubbr.Entity.user;
import com.Clubbr.Clubbr.utils.role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.Clubbr.Clubbr.Repository.userRepo;
import com.Clubbr.Clubbr.Dto.registerRequest;

import java.util.HashMap;
import java.util.Map;

@Service
public class authenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private jwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private userRepo userRepository;

    public authenticationResponse login(authenticationRequest authenticationRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUserID(), authenticationRequest.getPassword()
        );
        authenticationManager.authenticate(authToken);

        user user = userRepository.findById(authenticationRequest.getUserID()).get();

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));

        return new authenticationResponse(jwt);
    }

    public authenticationResponse register(registerRequest registerRequest){
        user newUser = user.builder()
                        .userID(registerRequest.getUserID())
                        .password(passwordEncoder.encode(registerRequest.getPassword()))
                        .name(registerRequest.getName())
                        .email(registerRequest.getEmail())
                        .surname(registerRequest.getSurname())
                        .country(registerRequest.getCountry())
                        .address(registerRequest.getAddress())
                        .userRole(role.USER)
                        .build();

        userRepository.save(newUser);

        return authenticationResponse.builder()
                .jwt(jwtService.generateToken(newUser, generateExtraClaims(newUser))).build();
    }

    private Map<String, Object> generateExtraClaims(user user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userID", user.getUserID());
        extraClaims.put("userRole", user.getUserRole());
        extraClaims.put("permissions", user.getAuthorities());

        return extraClaims;
    }
}

