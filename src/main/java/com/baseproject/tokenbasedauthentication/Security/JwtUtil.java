package com.baseproject.tokenbasedauthentication.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.access.token.key}")
    private String accessTokenSecretKey;
    @Value("${jwt.access.token.validTime}")
    private Long accessTokenValidTime;


    public Token createToken(String username, String role) {
        Date now = new Date();

        Claims claims = Jwts.claims()
                .setSubject("access_token")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime));

        claims.put("username", username);
        claims.put("role", role);

        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, accessTokenSecretKey)
                .compact();

        return new Token(token, accessTokenSecretKey);
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("access_token");
    }

}
