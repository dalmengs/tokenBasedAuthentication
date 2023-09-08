package com.baseproject.tokenbasedauthentication.Controller;

import com.baseproject.tokenbasedauthentication.BaseResponse;
import com.baseproject.tokenbasedauthentication.Dto.Authentication.AuthenticationRequestDto;
import com.baseproject.tokenbasedauthentication.Dto.Authentication.AuthenticationResponseDto;
import com.baseproject.tokenbasedauthentication.Entity.User;
import com.baseproject.tokenbasedauthentication.Exception.BaseException;
import com.baseproject.tokenbasedauthentication.Security.JwtUtil;
import com.baseproject.tokenbasedauthentication.Security.Token;
import com.baseproject.tokenbasedauthentication.Service.AuthenticationService;
import com.baseproject.tokenbasedauthentication.Service.TokenService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public BaseResponse login(@RequestBody AuthenticationRequestDto authenticationRequest, HttpServletResponse response){
        try{
            User user = authenticationService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());

            Token token = tokenService.createToken(user);
            response.setHeader("access_token", token.getAccessToken());

            tokenService.createRefreshToken(token);

            log.info("Access Token Created - Username : " + user.getUsername());
            return BaseResponse.OK(new AuthenticationResponseDto(token.getAccessToken(), user.getUsername()));
        }
        catch (BaseException e){
            return BaseResponse.FAILED(e.getResultCode(), e.getMsg());
        }
        catch (Exception e){
            return BaseResponse.FAILED();
        }
    }

}
