package com.delivery.mydelivery.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuOptionRepository optionRepository;

    @Autowired
    private MenuOptionContentRepository optionContentRepository;

    // 매장id로 메뉴 목록을 가져옴
    public List<MenuEntity> getMenuList(int storeId) {
        return menuRepository.findByStoreId(storeId);
    }

    // 메뉴id로 옵션 종류를 가져옴
    public List<MenuOptionEntity> getMenuOptionList(int menuId) {
        return optionRepository.findByMenuId(menuId);
    }

    // 메뉴옵션id로 옵션 내용을 가져옴
    public List<MenuOptionContentEntity> getMenuOptionContentList(int menuOptionId) {
        return optionContentRepository.findByMenuOptionId(menuOptionId);
    }
}
