package com.delivery.mydelivery.store;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends CrudRepository<StoreEntity, Integer> {

    List<StoreEntity> findByCategoryAndDeliveryAvailablePlace(String category, String deliveryAvailablePlace); // 카테고리를 통해 매장목록 검색

    StoreEntity findByStoreId(int storeId); // 매장 Id를 통해 검색

    List<StoreEntity> findByStoreNameContaining(String keyword); // 매장 검색

}
