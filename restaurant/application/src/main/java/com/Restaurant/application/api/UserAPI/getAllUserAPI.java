package com.Restaurant.application.api.UserAPI;

import com.Restaurant.application.entity.User;
import com.Restaurant.application.response.ApiOutput;
import com.Restaurant.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class getAllUserAPI {

    private final UserService userService;

    public ApiOutput<?> getAllUserApi() {
        List<User> users = userService.getAllUsers();
        return new ApiOutput<>(HttpStatus.OK.value(), "All users fetched successfully", users);

    }

}
