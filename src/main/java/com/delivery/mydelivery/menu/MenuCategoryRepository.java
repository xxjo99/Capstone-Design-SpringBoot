package com.delivery.mydelivery.menu;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuCategoryRepository extends CrudRepository<MenuCategoryEntity, Integer> {

    List<MenuCategoryEntity> findByStoreId(int storeId);


}
