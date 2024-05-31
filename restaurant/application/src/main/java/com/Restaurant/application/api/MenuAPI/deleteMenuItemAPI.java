package com.Restaurant.application.api.MenuAPI;


import com.Restaurant.application.dto.MenuItemDto;
import com.Restaurant.application.response.ApiOutput;
import com.Restaurant.application.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class deleteMenuItemAPI {
    private final MenuService menuService;

    public ApiOutput<?> deleteMenuItemApi(int menuId) {
        MenuItemDto deleted = menuService.deleteMenuItem(menuId);
        return new ApiOutput<>(HttpStatus.OK.value(),"Menu item deleted successfully.",deleted);
    }
}
