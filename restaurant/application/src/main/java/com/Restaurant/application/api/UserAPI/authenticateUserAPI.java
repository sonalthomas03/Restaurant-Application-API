package com.Restaurant.application.api.UserAPI;


import com.Restaurant.application.auth.AuthenticationRequest;
import com.Restaurant.application.auth.AuthenticationResponse;
import com.Restaurant.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component @RequiredArgsConstructor
public class authenticateUserAPI {


    private final UserService userService;

    public AuthenticationResponse authenticateUserApi(AuthenticationRequest request) {
        return userService.authenticate(request);
    }


}
