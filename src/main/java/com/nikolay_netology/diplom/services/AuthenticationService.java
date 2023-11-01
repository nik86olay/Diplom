package com.nikolay_netology.diplom.services;

import com.nikolay_netology.diplom.repository.AuthenticationRepository;
import com.nikolay_netology.diplom.util.jwt.TokenManager;
import com.nikolay_netology.diplom.util.request.AuthRequest;
import com.nikolay_netology.diplom.util.response.AuthResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationService {

    private AuthenticationRepository authenticationRepository;
    private AuthenticationManager authenticationManager;
    private TokenManager tokenManager;
    private UserService userService;

    public AuthResponse login(AuthRequest authRequest) throws Exception {
        final String username = authRequest.getLogin();
        final String password = authRequest.getPassword();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,
                            password)
            );
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        final UserDetails userDetails = userService.loadUserByUsername(username);
        final String jwtToken = tokenManager.generateJwtToken(userDetails);
        authenticationRepository.putTokenAndUsername(jwtToken, username);
        log.info("User {} authentication. JWT: {}", username, jwtToken);
        return new AuthResponse(jwtToken);
    }

    public void logout(String authToken) {
        final String jwtToken = authToken.substring(7);
        final String username = authenticationRepository.getUsernameByToken(jwtToken);
        log.info("User {} logout. JWT is disabled.", username);
        authenticationRepository.removeTokenAndUsernameByToken(jwtToken);
    }
}
