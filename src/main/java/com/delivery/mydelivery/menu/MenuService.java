package com.delivery.mydelivery.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private OptionContentRepository optionContentRepository;

    // 매장id로 메뉴 목록을 가져옴
    public List<MenuEntity> getMenuList(int storeId) {
        return menuRepository.findByStoreId(storeId);
    }

    // 메뉴id로 옵션 종류를 가져옴
    public List<OptionEntity> getMenuOptionList(int menuId) {
        return optionRepository.findByMenuId(menuId);
    }

    // 메뉴옵션id로 옵션 내용을 가져옴
    public List<OptionContentEntity> getMenuOptionContentList(int menuOptionId) {
        return optionContentRepository.findByMenuOptionId(menuOptionId);
    }

    // 메뉴 id를 통해 메뉴 이름 반환
    public String getMenuName(int menuId) {
        MenuEntity menu = menuRepository.findByMenuId(menuId);
        return menu.getMenuName();
    }

}
