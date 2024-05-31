package com.Restaurant.application.controller;


import com.Restaurant.application.api.OrderAPI.getAllOrdersAPI;
import com.Restaurant.application.api.OrderAPI.getAllOrdersForCurrentUserAPI;
import com.Restaurant.application.api.OrderAPI.placeOrderAPI;
import com.Restaurant.application.api.OrderAPI.updateOrderAPI;
import com.Restaurant.application.dto.OrderRequest;
import com.Restaurant.application.enums.OrderStatus;
import com.Restaurant.application.response.ApiOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurant/order")
@RequiredArgsConstructor
public class OrderController {
    private final placeOrderAPI placeOrderAPI;
    private final updateOrderAPI updateOrderAPI;
    private final getAllOrdersAPI getAllOrdersAPI;
    private final getAllOrdersForCurrentUserAPI getAllOrdersForCurrentUserAPI;


    @GetMapping("/getall")
    public ApiOutput<?> getAllOrders(){
        return getAllOrdersAPI.getAllOrdersApi();
    }

    @GetMapping("/get")
    public ApiOutput<?> getOrders(){
        return getAllOrdersForCurrentUserAPI.getAllOrdersForCurrentUserApi();
    }


    @PostMapping("/place")
    public ApiOutput<?> placeOrder(@RequestBody OrderRequest orderRequest) {
        return placeOrderAPI.placeOrderApi(orderRequest);
    }

    @PutMapping("/update/{id}")
    public ApiOutput<?> updateOrder(@PathVariable Long id, @RequestParam OrderStatus newStatus) {
        return updateOrderAPI.updateOrderApi(id,newStatus);
    }
}
