package com.nikolay_netology.diplom.service;

import com.nikolay_netology.diplom.services.AuthenticationService;
import com.nikolay_netology.diplom.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.AuthenticationManager;
import com.nikolay_netology.diplom.util.jwt.TokenManager;
import com.nikolay_netology.diplom.repository.AuthenticationRepository;

import static com.nikolay_netology.diplom.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private AuthenticationRepository authenticationRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenManager jwtTokenUtil;

    @Mock
    private UserService userService;

    @Test
    void login() throws Exception {
        Mockito.when(userService.loadUserByUsername(USERNAME_1)).thenReturn(USER_1);
        Mockito.when(jwtTokenUtil.generateJwtToken(USER_1)).thenReturn(TOKEN_1);
        assertEquals(AUTHENTICATION_RS, authenticationService.login(AUTHENTICATION_RQ));
        Mockito.verify(authenticationManager, Mockito.times(1)).authenticate(USERNAME_PASSWORD_AUTHENTICATION_TOKEN);
        Mockito.verify(authenticationRepository, Mockito.times(1)).putTokenAndUsername(TOKEN_1, USERNAME_1);
    }

    @Test
    void logout() {
        Mockito.when(authenticationRepository.getUsernameByToken(BEARER_TOKEN_SUBSTRING_7)).thenReturn(USERNAME_1);
        authenticationService.logout(BEARER_TOKEN);
        Mockito.verify(authenticationRepository, Mockito.times(1)).getUsernameByToken(BEARER_TOKEN_SUBSTRING_7);
        Mockito.verify(authenticationRepository, Mockito.times(1)).removeTokenAndUsernameByToken(BEARER_TOKEN_SUBSTRING_7);
    }
}