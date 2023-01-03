package com.delivery.mydelivery.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreRepository repository;

    // 넘겨받은 카테고리 데이터를 통해 매장 검색
    public List<StoreEntity> getStoreList(String category) {
        List<StoreEntity> storeList = new ArrayList<>();
        storeList = repository.findByCategory(category);
        return storeList;
    }
}
