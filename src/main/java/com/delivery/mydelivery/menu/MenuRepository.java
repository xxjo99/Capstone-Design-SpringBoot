package com.delivery.mydelivery.menu;

import com.delivery.mydelivery.store.StoreEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends CrudRepository<MenuEntity, Integer> {

    List<MenuEntity> findByStoreId(int storeId); // 매장 id를 통해 메뉴목록 검색

    MenuEntity findByMenuId(int menuId); // 메뉴 id를 통해 메뉴 검색

}
