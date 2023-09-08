package com.baseproject.tokenbasedauthentication.Service;

import com.baseproject.tokenbasedauthentication.Entity.RefreshToken;
import com.baseproject.tokenbasedauthentication.Entity.User;
import com.baseproject.tokenbasedauthentication.Exception.BaseException;
import com.baseproject.tokenbasedauthentication.Repository.RefreshTokenRepository;
import com.baseproject.tokenbasedauthentication.Repository.UserRepository;
import com.baseproject.tokenbasedauthentication.Security.JwtUtil;
import com.baseproject.tokenbasedauthentication.Security.Token;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RefreshTokenRepository tokenRepository;
    private final JwtUtil jwtUtil;

    public User login(String username, String password) throws Exception{
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()) throw new BaseException(404, "User Not Found");

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new BaseException(404, "User Not Found");
        }

        return user.get();
    }

}
