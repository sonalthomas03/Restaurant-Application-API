package com.Restaurant.application.api.UserAPI;

import com.Restaurant.application.dto.SearchRequest;
import com.Restaurant.application.dto.UserDto;
import com.Restaurant.application.entity.User;
import com.Restaurant.application.response.ApiOutput;
import com.Restaurant.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class findAllByCriteriaAPI {
    private final UserService userService;

    public ApiOutput<?> findAllByCriteriaApi(SearchRequest request)
    {
        List<User> filtered = userService.findAllByCriteria(request);
        return new ApiOutput<>(HttpStatus.OK.value(), "Users fetched successfully", filtered);

    }
}
