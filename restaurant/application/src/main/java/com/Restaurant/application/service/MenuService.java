package com.Restaurant.application.service;


import com.Restaurant.application.dto.MenuItemDto;
import com.Restaurant.application.entity.MenuItem;
import com.Restaurant.application.exception.exceptions.ItemAlreadyExistsException;
import com.Restaurant.application.exception.exceptions.entityNotExistException;
import com.Restaurant.application.exception.exceptions.tableEmptyException;
import com.Restaurant.application.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuService {

    @Autowired
    private ModelMapper modelMapper;

    private final MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public boolean isMenuTableEmpty()
    {
        return menuRepository.count() == 0;
    }

    public List<MenuItem> getMenu()
    {
        if(isMenuTableEmpty())
            throw new tableEmptyException("Menu table is empty.");

        return menuRepository.findAll();

        /*
        return menuItems.stream()
                .map(menuItem -> modelMapper.map(menuItem, MenuItemDto.class))
                .collect(Collectors.toList());

         */
    }

    public boolean isFoodItemExists(String foodName) {
        Optional<MenuItem> existingItem = menuRepository.findByName(foodName);
        return existingItem.isPresent();
    }

    public MenuItemDto addNewMenuItem(MenuItem menuItem)
    {
        if (isFoodItemExists(menuItem.getName())) {
            throw new ItemAlreadyExistsException("Food item with name " + menuItem.getName() + " already exists.");
        }

        menuRepository.save(menuItem);
        return modelMapper.map(menuItem, MenuItemDto.class);

    }

    public MenuItemDto deleteMenuItem(int id)
    {
        boolean exists = menuRepository.existsById(id);
        if(!exists)
            throw new entityNotExistException("Menu item does not exist.");

        MenuItemDto deleted = modelMapper.map(menuRepository.findById(id), MenuItemDto.class);
        menuRepository.deleteById(id);
        return deleted;
    }

    public MenuItemDto updateMenuItem(int id, String name, String type, Integer cost)
    {
        MenuItem foodItem = menuRepository.findById(id)
                .orElseThrow(()-> new entityNotExistException("Menu item does not exist."));

        if(name!=null &&
                name.length()>0 &&
                !Objects.equals(foodItem.getName(),name))
            foodItem.setName(name);


        if(type!=null &&
                type.length()>0 &&
                !Objects.equals(foodItem.getType(),type))
            foodItem.setType(type);

        if(cost!=null && Objects.equals(foodItem.getCost(),cost))
            foodItem.setCost(cost);

        menuRepository.save(foodItem);
        return modelMapper.map(foodItem, MenuItemDto.class);
    }





}
