package com.nikolay_netology.diplom.util.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class RequestAuth {
    @NotEmpty(message = "login must not be null")
    @Email
    private String login;

    @NotEmpty(message = "password must not be null")
    private String password;
}
