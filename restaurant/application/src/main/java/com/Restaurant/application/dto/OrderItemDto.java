package com.Restaurant.application.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private String name;
    private int quantity;


    public OrderItemDto(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}