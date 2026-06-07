package com.foodgest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import com.foodgest.users.entities.UserEntities;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public String generateToken(UserDetails userDetails) {
        return jwtTokenUtil.generateToken(userDetails);
    }

    public String generateToken(UserEntities usuario) {
        return jwtTokenUtil.generateToken(usuario);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return jwtTokenUtil.validateToken(token, userDetails);
    }

    public String getUsernameFromToken(String token) {
        return jwtTokenUtil.extractUsername(token);
    }
}

