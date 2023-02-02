package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.menu.MenuEntity;
import com.delivery.mydelivery.menu.OptionContentEntity;
import com.delivery.mydelivery.menu.OptionEntity;
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

    // 해당 매장의 메뉴가져옴
    @GetMapping("/menu/{storeId}")
    public List<MenuEntity> getMenuList(@PathVariable int storeId) {
        return menuService.getMenuList(storeId);
    }

    // 해당 메뉴의 옵션 가져옴
    @GetMapping("/menu/option/{menuId}")
    public List<OptionEntity> getMenuOptionList(@PathVariable int menuId) {
        return menuService.getMenuOptionList(menuId);
    }

    // 해당 옵션의 내용 가져옴
    @GetMapping("/menu/option/content/{menuOptionId}")
    public List<OptionContentEntity> getMenuOptionContentList(@PathVariable int menuOptionId) {
        return menuService.getMenuOptionContentList(menuOptionId);
    }

    // 메뉴 반환
    @GetMapping("/menu/getMenu/{menuId}")
    public MenuEntity getMenu(@PathVariable int menuId) {
        return menuService.getMenu(menuId);
    }

}
