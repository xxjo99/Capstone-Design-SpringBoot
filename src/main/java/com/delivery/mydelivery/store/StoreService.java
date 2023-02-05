package com.delivery.mydelivery.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreRepository repository;

    // 카테고리, 배달가능지역을 통해 매장 검색
    public List<StoreEntity> getStoreList(String category, String deliveryAvailablePlace) {
        List<StoreEntity> storeList = new ArrayList<>();
        storeList = repository.findByCategoryAndDeliveryAvailablePlace(category, deliveryAvailablePlace);
        return storeList;
    }

    public StoreEntity getStore(int storeId) {
        return repository.findByStoreId(storeId);
    }

    // 매장 검색
    public List<StoreEntity> searchStore(String keyword, String deliveryAvailablePlace) {
        List<StoreEntity> storeList = repository.findByStoreNameContaining(keyword);

        List<StoreEntity> storeListResult = new ArrayList<>();
        for (StoreEntity store : storeList) {
            String deliveryAvailable = store.getDeliveryAvailablePlace();

            if (deliveryAvailable.equals(deliveryAvailablePlace)) {
                storeListResult.add(store);
            }
        }

        return storeListResult;

    }

}
