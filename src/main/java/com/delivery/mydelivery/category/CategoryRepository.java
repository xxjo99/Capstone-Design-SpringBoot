package com.delivery.mydelivery.category;

import com.delivery.mydelivery.store.StoreEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<StoreEntity, Integer> {

    public List<StoreEntity> findByCategory(String category);

}
