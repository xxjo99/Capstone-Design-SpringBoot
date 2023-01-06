package com.delivery.mydelivery.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository repository;

    // 매장id로 메뉴 목록을 가져옴
    public List<MenuEntity> getMenuList(int storeId) {
        List<MenuEntity> menuList = new ArrayList<>();
        menuList = repository.findByStoreId(storeId);
        return menuList;
    }
}
