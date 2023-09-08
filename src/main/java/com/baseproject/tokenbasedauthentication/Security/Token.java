package com.baseproject.tokenbasedauthentication.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.apache.catalina.core.ApplicationContext;
import org.apache.catalina.core.ApplicationFilterConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.security.Key;
import java.util.Date;

@AllArgsConstructor
public class Token {

    private final String accessToken;
    private final String secretKey;

    public String getUsername(){
        return (String) Jwts.parserBuilder()
                .setSigningKey(this.secretKey)
                .build()
                .parseClaimsJws(this.accessToken)
                .getBody()
                .get("username");
    }

    public String getAccessToken(){
        return this.accessToken;
    }

    public boolean validateToken() {
        try {
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(this.secretKey)
                    .build()
                    .parseClaimsJws(this.accessToken)
                    .getBody();

            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
