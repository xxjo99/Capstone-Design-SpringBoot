package com.delivery.mydelivery.menu;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuOptionContentRepository extends CrudRepository<MenuOptionContentEntity, Integer> {

    List<MenuOptionContentEntity> findByMenuOptionId(int menuOptionId); // menuOptionId를 통해 옵션의 내용 검색

}
