package com.nikolay_netology.diplom.controller;

import com.nikolay_netology.diplom.services.AuthenticationService;
import com.nikolay_netology.diplom.util.UserErrorResponse;
import com.nikolay_netology.diplom.util.UserNotCreatedException;
import com.nikolay_netology.diplom.util.request.RequestAuth;
import com.nikolay_netology.diplom.util.response.ResponseAuth;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseAuth login(@RequestBody @Valid RequestAuth requestAuth, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).
                        append(";");
            }
            throw new UserNotCreatedException(errorMsg.toString());
        }
        return authenticationService.login(requestAuth);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("auth-token") String authToken) {
        authenticationService.logout(authToken);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handlerException(UserNotCreatedException e){
        UserErrorResponse response = new UserErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
