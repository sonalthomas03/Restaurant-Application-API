package com.Restaurant.application.dto;

import com.Restaurant.application.enums.OrderStatus;
import lombok.Data;

import java.util.List;
@Data
public class OrderDto {
    private String user;
    private List<OrderItemDto> items;
    private OrderStatus status;


}
