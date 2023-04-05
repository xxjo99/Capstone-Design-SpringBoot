package com.delivery.mydelivery.menu;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends CrudRepository<MenuEntity, Integer> {

    List<MenuEntity> findByStoreIdAndMenuCategoryId(int storeId, int menuCategoryId);

    MenuEntity findByMenuId(int menuId); // 메뉴 id를 통해 메뉴 검색

}
