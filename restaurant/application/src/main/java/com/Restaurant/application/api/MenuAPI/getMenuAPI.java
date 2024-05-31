package com.Restaurant.application.api.MenuAPI;

import com.Restaurant.application.dto.MenuItemDto;
import com.Restaurant.application.entity.MenuItem;
import com.Restaurant.application.response.ApiOutput;
import com.Restaurant.application.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component @RequiredArgsConstructor
public class getMenuAPI {

    private final MenuService menuService;

    public ApiOutput<?> getMenuApi(){
        List<MenuItem> fetched = menuService.getMenu();
        return new ApiOutput<>(HttpStatus.OK.value(),"Menu fetched added successfully.",fetched);
    }


}
