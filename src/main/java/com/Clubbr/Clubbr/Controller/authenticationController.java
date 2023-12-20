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

    @PostMapping("/login")
    public ResponseEntity<authenticationResponse> login(@RequestBody @Valid authenticationRequest authenticationRequest) {
        authenticationResponse jwtDto = authenticationService.login(authenticationRequest);

        return ResponseEntity.ok(jwtDto);
    }

    @PostMapping("/register")
    public ResponseEntity<authenticationResponse> register(@RequestBody @Valid registerRequest registerRequest) {
        authenticationResponse jwtDto = authenticationService.register(registerRequest);

        return ResponseEntity.ok(jwtDto);
    }
}
