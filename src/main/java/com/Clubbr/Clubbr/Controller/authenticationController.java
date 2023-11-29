package com.Clubbr.Clubbr.Controller;

import com.Clubbr.Clubbr.Dto.authenticationRequest;
import com.Clubbr.Clubbr.Dto.authenticationResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.Clubbr.Clubbr.Service.authenticationService;

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

    //TEST
    @GetMapping("/public-access")
    public String publicAccessEndpoint() {
        return "Endpoint de acesso público";
    }
}
