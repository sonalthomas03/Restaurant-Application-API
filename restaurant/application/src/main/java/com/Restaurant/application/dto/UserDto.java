package com.Restaurant.application.dto;

import com.Restaurant.application.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserDto {
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
}
