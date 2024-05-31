package com.Restaurant.application.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Data @Entity @Table @AllArgsConstructor
@NoArgsConstructor
@EnableWebMvc
public class MenuItem {
    @JsonIgnore
    @Valid
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Food name is mandatory.")//DTO level
    private String name;

    @NotBlank(message = "Food type is mandatory.")
    private String type;

    @NotNull(message = "Food cost is mandatory.")
    private Integer cost;

    public MenuItem(String name, String type, Integer cost) {
        this.name = name;
        this.type = type;
        this.cost = cost;
    }


}


