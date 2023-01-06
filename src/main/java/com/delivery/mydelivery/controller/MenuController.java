package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.menu.MenuEntity;
import com.delivery.mydelivery.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {

    @Autowired
    MenuService menuService;

    @GetMapping("/menuList/{storeId}")
    public List<MenuEntity> setMenuList(@PathVariable int storeId) {
        return menuService.getMenuList(storeId);
    }

}
