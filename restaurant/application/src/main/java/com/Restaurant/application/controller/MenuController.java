package com.Restaurant.application.controller;

import com.Restaurant.application.api.MenuAPI.addNewMenuItemAPI;
import com.Restaurant.application.api.MenuAPI.deleteMenuItemAPI;
import com.Restaurant.application.api.MenuAPI.getMenuAPI;
import com.Restaurant.application.entity.MenuItem;
import com.Restaurant.application.response.ApiOutput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(path="api/restaurant/menu")
public class MenuController {

    private final addNewMenuItemAPI addNewMenuItemAPI;
    private final getMenuAPI getMenuAPI;
    private final deleteMenuItemAPI deleteMenuAPI;
    private final com.Restaurant.application.api.MenuAPI.updateMenuItemAPI updateMenuItemAPI;

    //@PreAuthorize("hasRole('STAFF')")
    @PostMapping("/add")
    public ApiOutput<?> addNewMenuItem(@Valid @RequestBody MenuItem menuItem) {
        return addNewMenuItemAPI.addNewMenuItemApi(menuItem);
    }

    @GetMapping("/get")
    public ApiOutput<?> getMenu()
    {
        return getMenuAPI.getMenuApi();
    }

    //@PreAuthorize("hasRole('STAFF')")
    @DeleteMapping("/delete/{id}")
    public ApiOutput<?> deleteMenu(@PathVariable int id)
    {
        return deleteMenuAPI.deleteMenuItemApi(id);
    }

    //@PreAuthorize("hasRole('STAFF')")
    @PutMapping("/update/{id}")
    public ApiOutput<?> updateMenu(
            @PathVariable int id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer cost)
    {
        return updateMenuItemAPI.updateMenuItemApi(id, name, type, cost);
    }


}
