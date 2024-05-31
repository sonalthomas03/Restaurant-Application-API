package com.Restaurant.application.api.MenuAPI;

import com.Restaurant.application.dto.MenuItemDto;
import com.Restaurant.application.exception.exceptions.InvalidEntityDataException;
import com.Restaurant.application.response.ApiOutput;
import com.Restaurant.application.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.Restaurant.application.utilities.ValidationUtils.isValidCost;
import static com.Restaurant.application.utilities.ValidationUtils.isValidString;

@Component
@RequiredArgsConstructor
public class updateMenuItemAPI {
    private final MenuService menuService;


    public ApiOutput<?> updateMenuItemApi(int id, String name, String type, Integer cost )
    {
        try{
            validateMenuData(name,type,cost);
            MenuItemDto updated = menuService.updateMenuItem(id, name, type, cost);
            return new ApiOutput<>(HttpStatus.OK.value(),"Menu item updated successfully.",updated);
        } catch (InvalidEntityDataException e) {
            return new ApiOutput<>(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
        }
    }

    private void validateMenuData(String name,String type, Integer cost) {
        if(!isValidString(name))
            throw new InvalidEntityDataException("Dish name is invalid");
        if(!isValidString(type))
            throw new InvalidEntityDataException("Dish type is invalid");
        if(!isValidCost(cost))
            throw new InvalidEntityDataException("Dish cost is invalid");

    }
}
