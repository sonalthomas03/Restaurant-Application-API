package com.Restaurant.application.dto;

import lombok.Data;

@Data
public class SearchRequest {

    private String firstname;
    private String lastname;
    private String email;
}
