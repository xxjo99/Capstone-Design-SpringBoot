package com.delivery.mydelivery.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuCategoryRepository menuCategoryRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private OptionContentRepository optionContentRepository;

    // 매장 id로 메뉴 카테고리 목록 목록
    public List<MenuCategoryEntity> getMenuCategoryList(int storeId) {
        return menuCategoryRepository.findByStoreId(storeId);
    }

    // 매장id, 카테고리 id로 메뉴 목록 검색
    public List<MenuEntity> getMenuList(int storeId, int menuCategoryId) {
        return menuRepository.findByStoreIdAndMenuCategoryId(storeId, menuCategoryId);
    }

    // 메뉴id로 옵션 종류 검색
    public List<OptionEntity> getMenuOptionList(int menuId) {
        return optionRepository.findByMenuId(menuId);
    }

    // 메뉴옵션id로 옵션 내용 검색
    public List<OptionContentEntity> getMenuOptionContentList(int menuOptionId) {
        return optionContentRepository.findByMenuOptionId(menuOptionId);
    }

    // 메뉴 id를 통해 메뉴 반환
    public MenuEntity getMenu(int menuId) {
        return menuRepository.findByMenuId(menuId);
    }

}
