package com.nikolay_netology.diplom.services;

import com.nikolay_netology.diplom.repository.AuthenticationRepository;
import com.nikolay_netology.diplom.util.jwt.TokenManager;
import com.nikolay_netology.diplom.util.request.RequestAuth;
import com.nikolay_netology.diplom.util.response.ResponseAuth;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private AuthenticationRepository authenticationRepository;
    private AuthenticationManager authenticationManager;
    private TokenManager tokenManager;
    private UserService userService;

    public ResponseAuth login(RequestAuth requestAuth) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestAuth.getLogin(),
                            requestAuth.getPassword())
            );
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        final UserDetails userDetails = userService.loadUserByUsername(requestAuth.getLogin());
        final String jwtToken = tokenManager.generateJwtToken(userDetails);
        return new ResponseAuth(jwtToken);
    }

    public void logout(String authToken) {
        final String token = authToken.substring(7);
        authenticationRepository.removeTokenAndUsernameByToken(token);
    }
}
