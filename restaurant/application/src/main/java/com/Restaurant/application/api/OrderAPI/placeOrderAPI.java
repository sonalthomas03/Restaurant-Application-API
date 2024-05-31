package com.Restaurant.application.api.OrderAPI;

import com.Restaurant.application.dto.OrderDto;
import com.Restaurant.application.dto.OrderRequest;
import com.Restaurant.application.entity.Order;
import com.Restaurant.application.response.ApiOutput;
import com.Restaurant.application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class placeOrderAPI {

    private final OrderService orderService;

    public ApiOutput<?> placeOrderApi(OrderRequest orderRequest)
    {
        OrderDto placed = orderService.placeOrder(orderRequest);
        return new ApiOutput<>(HttpStatus.OK.value(),"Order placed successfully.",placed);
    }
}
