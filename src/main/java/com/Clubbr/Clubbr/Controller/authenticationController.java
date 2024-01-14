package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Dto.authenticationRequest;
import com.Clubbr.Clubbr.Dto.authenticationResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Service.authenticationService;
import com.Clubbr.Clubbr.Dto.registerRequest;

@RestController
@RequestMapping("/authentication")
public class authenticationController {

    @Autowired
    private authenticationService authenticationService;


    /**
     * This method is used to authenticate a user and generate a JWT token for them.
     *
     * @param authenticationRequest This is a DTO that contains the user's ID and password.
     *
     * @custom.inputformat {
     *     "userID": "string",
     *     "password": "string"
     * }
     * @return authenticationResponse This returns a DTO that contains the generated JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<authenticationResponse> login(@RequestBody @Valid authenticationRequest authenticationRequest) {
        authenticationResponse jwtDto = authenticationService.login(authenticationRequest);

        return ResponseEntity.ok(jwtDto);
    }

    /**
     * This method is used to register a new user.
     *
     * @param registerRequest This is a DTO that contains the new user's details.
     *                        <p>The user's ID must be unique.</p>
     *                        <p>The user's role can be null, in which case it will be set to "USER".</p>
     *
     * @custom.inputformat {
     *    "userID": "string",
     *    "password": "string",
     *    "name": "string",
     *    "surname": "string",
     *    "email": "string",
     *    "phone": "string",
     *    "address": "string",
     *    "country": "string",
     *    "role": "string"
     *    }
     *
     * @return authenticationResponse This returns a DTO that contains the generated JWT token.
     */
    @PostMapping("/register")
    public ResponseEntity<authenticationResponse> register(@RequestBody @Valid registerRequest registerRequest) {
        authenticationResponse jwtDto = authenticationService.register(registerRequest);

        return ResponseEntity.ok(jwtDto);
    }
}
