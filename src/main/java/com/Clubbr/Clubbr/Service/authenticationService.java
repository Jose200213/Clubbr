package com.Clubbr.Clubbr.Service;

import com.Clubbr.Clubbr.Dto.authenticationRequest;
import com.Clubbr.Clubbr.Dto.authenticationResponse;
import com.Clubbr.Clubbr.Entity.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.Clubbr.Clubbr.Repository.userRepo;

import java.util.HashMap;
import java.util.Map;

@Service
public class authenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private jwtService jwtService;

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

    private Map<String, Object> generateExtraClaims(user user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userID", user.getUserID());
        extraClaims.put("userRole", user.getUserRole());
        extraClaims.put("permissions", user.getAuthorities());

        return extraClaims;
    }
}

