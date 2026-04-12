package com.meubolso.api.web;

import com.meubolso.api.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private final JwtTokenProvider tokenProvider;

    public AuthController(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        if (!"user@example.com".equals(request.email()) || !"password".equals(request.password())) {
            return ResponseEntity.badRequest().build();
        }

        String token = tokenProvider.createToken("default-user", request.email());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
