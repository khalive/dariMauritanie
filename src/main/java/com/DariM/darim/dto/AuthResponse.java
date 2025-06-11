package com.DariM.darim.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String jwtToken;
     public AuthResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    // Getter and setter
    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}

