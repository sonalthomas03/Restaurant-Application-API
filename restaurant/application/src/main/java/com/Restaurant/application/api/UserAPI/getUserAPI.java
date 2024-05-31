package com.Restaurant.application.api.UserAPI;

import com.Restaurant.application.dto.UserDto;
import com.Restaurant.application.entity.User;
import com.Restaurant.application.response.ApiOutput;
import com.Restaurant.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class getUserAPI {

    private final UserService userService;

    public ApiOutput<?> getUserApi() {
        UserDto fetched = userService.getUser();
        return new ApiOutput<>(HttpStatus.OK.value(), "User fetched successfully", fetched);
    }
}
