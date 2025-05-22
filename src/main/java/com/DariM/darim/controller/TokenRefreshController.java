package com.DariM.darim.controller;

import com.DariM.darim.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class TokenRefreshController {

    private final JwtUtil jwtUtil;

    @Autowired
    public TokenRefreshController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
        try {
            String newToken = jwtUtil.refreshToken(request.token());
            return ResponseEntity.ok()
                .header("Authorization", "Bearer " + newToken)
                .header("Access-Control-Expose-Headers", "Authorization")
                .body(new TokenRefreshResponse(newToken, "Bearer"));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(401)
                .body("{\"error\":\"Token too old for refresh\"}");
        } catch (Exception e) {
            return ResponseEntity.status(401)
                .body("{\"error\":\"Invalid refresh token\"}");
        }
    }

    record TokenRefreshRequest(String token) {}
    record TokenRefreshResponse(String token, String tokenType) {
        public TokenRefreshResponse(String token) {
            this(token, "Bearer");
        }
    }
}
