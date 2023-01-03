package com.delivery.mydelivery.store;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends CrudRepository<StoreEntity, Integer> {

    public List<StoreEntity> findByCategory(String category);

}
