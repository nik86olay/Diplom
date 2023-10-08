package com.nikolay_netology.diplom.util.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    @NotEmpty(message = "login must not be null")
    @Email
    private String login;

    @NotEmpty(message = "password must not be null")
    private String password;
}
