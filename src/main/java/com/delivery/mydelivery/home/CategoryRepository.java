package com.delivery.mydelivery.home;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

    List<CategoryEntity> findAll();

}
