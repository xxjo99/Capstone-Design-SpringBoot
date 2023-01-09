package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.menu.MenuEntity;
import com.delivery.mydelivery.menu.MenuOptionContentEntity;
import com.delivery.mydelivery.menu.MenuOptionEntity;
import com.delivery.mydelivery.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/menuList/{storeId}")
    public List<MenuEntity> getMenuList(@PathVariable int storeId) {
        return menuService.getMenuList(storeId);
    }

    @GetMapping("/optionList/{menuId}")
    public List<MenuOptionEntity> getMenuOptionList(@PathVariable int menuId) {
        return menuService.getMenuOptionList(menuId);
    }

    @GetMapping("/optionList/option/{menuOptionId}")
    public List<MenuOptionContentEntity> getMenuOptionContentList(@PathVariable int menuOptionId) {
        return menuService.getMenuOptionContentList(menuOptionId);
    }

}
