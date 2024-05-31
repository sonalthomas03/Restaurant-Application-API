package com.Restaurant.application.dto;

import com.Restaurant.application.entity.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<OrderItemDto> items;
}