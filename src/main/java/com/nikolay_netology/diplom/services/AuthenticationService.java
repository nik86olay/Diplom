package com.nikolay_netology.diplom.services;

import com.nikolay_netology.diplom.util.request.RequestAuth;
import com.nikolay_netology.diplom.util.response.ResponseAuth;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public ResponseAuth login(RequestAuth requestAuth) {
        requestAuth.getLogin()
    }
}
