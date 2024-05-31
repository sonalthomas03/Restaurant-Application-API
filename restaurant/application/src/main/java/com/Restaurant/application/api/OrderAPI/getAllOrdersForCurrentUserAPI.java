package com.Restaurant.application.api.OrderAPI;

import com.Restaurant.application.dto.OrderDto;
import com.Restaurant.application.dto.OrderRequest;
import com.Restaurant.application.response.ApiOutput;
import com.Restaurant.application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class getAllOrdersForCurrentUserAPI {

    private final OrderService orderService;


    public ApiOutput<?> getAllOrdersForCurrentUserApi()
    {
        List<OrderDto> get = orderService.getAllOrdersForCurrentUser();
        return new ApiOutput<>(HttpStatus.OK.value(),"All orders fetched successfully.",get);
    }
}
