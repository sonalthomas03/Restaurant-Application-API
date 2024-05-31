package com.Restaurant.application.api.MenuAPI;


import com.Restaurant.application.dto.MenuItemDto;
import com.Restaurant.application.entity.MenuItem;
import com.Restaurant.application.exception.exceptions.InvalidEntityDataException;
import com.Restaurant.application.service.MenuService;
import com.Restaurant.application.response.ApiOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.Restaurant.application.utilities.ValidationUtils.isValidCost;
import static com.Restaurant.application.utilities.ValidationUtils.isValidString;

@Component @RequiredArgsConstructor
public class addNewMenuItemAPI {
    private final MenuService menuService;



    public ApiOutput<?> addNewMenuItemApi(MenuItem menuItem) {
        try{
            validateMenuData(menuItem.getName(), menuItem.getType(), menuItem.getCost());
            MenuItemDto added  = menuService.addNewMenuItem(menuItem);
            return new ApiOutput<>(HttpStatus.OK.value(),"Menu item added successfully.",added);
        } catch (InvalidEntityDataException e) {
            return new ApiOutput<>(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
        }
    }

    private void validateMenuData(String name,String type, int cost) {
        if(!isValidString(name))
            throw new InvalidEntityDataException("Dish name is invalid");
        if(!isValidString(type))
            throw new InvalidEntityDataException("Dish type is invalid");
        if(!isValidCost(cost))
            throw new InvalidEntityDataException("Dish cost is invalid");

    }
}
