package com.baseproject.tokenbasedauthentication.Service;

import com.baseproject.tokenbasedauthentication.Entity.RefreshToken;
import com.baseproject.tokenbasedauthentication.Entity.User;
import com.baseproject.tokenbasedauthentication.Repository.RefreshTokenRepository;
import com.baseproject.tokenbasedauthentication.Security.JwtUtil;
import com.baseproject.tokenbasedauthentication.Security.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository tokenRepository;

    public Token createToken(User user){
        return jwtUtil.createToken(user.getUsername(), user.getRole());
    }

    public RefreshToken createRefreshToken(Token token){
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUsername(token.getUsername());
        refreshToken.setAccessToken(token.getAccessToken());
        refreshToken.setExpireTime(LocalDateTime.now().plusWeeks(2));

        return tokenRepository.save(refreshToken);
    }

    public RefreshToken validateRefreshToken(Token token){
        Optional<RefreshToken> refreshToken = tokenRepository.findByAccessToken(token.getAccessToken());
        if(refreshToken.isEmpty()) return null;

        if(LocalDateTime.now().isAfter(refreshToken.get().getExpireTime())) return null;
        return refreshToken.get();
    }

}
