package com.baseproject.tokenbasedauthentication.Security;

import com.baseproject.tokenbasedauthentication.Entity.User;
import com.baseproject.tokenbasedauthentication.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()){
            return null;
        }

        return new UserDetailsImpl(user.get());
    }

    public Authentication getAuthenticationByToken(Token token) {
        UserDetails userDetails = loadUserByUsername(token.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
