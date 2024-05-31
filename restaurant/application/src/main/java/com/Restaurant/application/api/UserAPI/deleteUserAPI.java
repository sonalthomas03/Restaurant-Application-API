package com.Restaurant.application.api.UserAPI;

import com.Restaurant.application.dto.UserDto;
import com.Restaurant.application.response.ApiOutput;
import com.Restaurant.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class deleteUserAPI {

    private final UserService userService;

    public ApiOutput<?> deleteUserApi(Long id)
    {
        UserDto deleted = userService.deleteUser(id);
        return new ApiOutput<>(HttpStatus.OK.value(), "User deleted successfully", deleted);

    }
}
