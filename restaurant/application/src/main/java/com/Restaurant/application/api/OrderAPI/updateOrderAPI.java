package com.Restaurant.application.api.OrderAPI;

import com.Restaurant.application.dto.OrderDto;
import com.Restaurant.application.dto.OrderRequest;
import com.Restaurant.application.enums.OrderStatus;
import com.Restaurant.application.response.ApiOutput;
import com.Restaurant.application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class updateOrderAPI {

    private final OrderService orderService;
    public ApiOutput<?> updateOrderApi(Long id, OrderStatus newStatus)
    {
        OrderDto updated = orderService.updateOrder(id,newStatus);
        return new ApiOutput<>(HttpStatus.OK.value(),"Order updated successfully.",updated);
    }
}
