package com.baseproject.tokenbasedauthentication.Security;

import com.baseproject.tokenbasedauthentication.Entity.RefreshToken;
import com.baseproject.tokenbasedauthentication.Entity.User;
import com.baseproject.tokenbasedauthentication.Repository.UserRepository;
import com.baseproject.tokenbasedauthentication.Service.AuthenticationService;
import com.baseproject.tokenbasedauthentication.Service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final TokenService tokenService;
    @Value("${jwt.access.token.key}")
    private String accessTokenSecretKey;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String tokenValue = jwtUtil.resolveToken((HttpServletRequest)request);

        log.info("JWT Filter Start");
        if(tokenValue != null){
            Token token = new Token(tokenValue, accessTokenSecretKey);

            if(!token.validateToken()){
                log.info("Invalid Access Token");

                RefreshToken refreshToken = tokenService.validateRefreshToken(token);
                if(refreshToken != null){
                    log.info("Find Valid Refresh Token");

                    Optional<User> user = userRepository.findByUsername(refreshToken.getUsername());

                    if(user.isPresent()){
                        log.info("Create New Tokens - Username : " + user.get().getUsername());
                        Token newToken = jwtUtil.createToken(user.get().getUsername(), user.get().getRole());
                        tokenService.createRefreshToken(newToken);

                        ((HttpServletResponse)response).setHeader("access_token", newToken.getAccessToken());

                        Authentication authentication = userDetailsService.getAuthenticationByToken(newToken);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
            else{
                log.info("Valid Access Token - Username : " + token.getUsername());
                Authentication authentication = userDetailsService.getAuthenticationByToken(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }


}
