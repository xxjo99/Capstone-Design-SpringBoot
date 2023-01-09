package com.delivery.mydelivery.menu;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuOptionRepository extends CrudRepository<MenuOptionEntity, Integer> {

    List<MenuOptionEntity> findByMenuId(int menuId); // 메뉴 id를 통해 해당 메뉴의 옵션 종류 검색
}
