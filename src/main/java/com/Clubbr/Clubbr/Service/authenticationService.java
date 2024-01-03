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


    /**
     * This method is used to authenticate a user and generate a JWT token for them.
     *
     * @param authenticationRequest This is a DTO that contains the user's ID and password.
     * @return authenticationResponse This returns a DTO that contains the generated JWT token.
     */
    public authenticationResponse login(authenticationRequest authenticationRequest) {
        // Create an authentication token using the user's ID and password
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUserID(), authenticationRequest.getPassword()
        );

        // Authenticate the user using the authentication manager
        authenticationManager.authenticate(authToken);

        // Retrieve the user from the repository using the user's ID
        user user = userRepository.findById(authenticationRequest.getUserID()).get();

        // Generate a JWT token for the user
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));

        // Return the JWT token wrapped in an authentication response
        return new authenticationResponse(jwt);
    }


    /**
     * This method is used to register a new user and generate a JWT token for them.
     *
     * @param registerRequest This is a DTO that contains the new user's details.
     * @return authenticationResponse This returns a DTO that contains the generated JWT token.
     */
    public authenticationResponse register(registerRequest registerRequest){
        // Create a new user using the details provided in the register request
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

        // Save the new user to the repository
        userRepository.save(newUser);

        // Generate a JWT token for the new user
        return authenticationResponse.builder()
                .jwt(jwtService.generateToken(newUser, generateExtraClaims(newUser))).build();
    }

    /**
     * This method is used to generate extra claims for a JWT token.
     *
     * @param user This is the user for whom the JWT token is being generated.
     * @return extraClaims This returns a map that contains the user's ID, role, and authorities.
     */
    private Map<String, Object> generateExtraClaims(user user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userID", user.getUserID());
        extraClaims.put("userRole", user.getUserRole());
        extraClaims.put("permissions", user.getAuthorities());

        return extraClaims;
    }
}

