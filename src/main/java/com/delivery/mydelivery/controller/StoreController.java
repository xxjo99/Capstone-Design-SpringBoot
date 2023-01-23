package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.store.StoreService;
import com.delivery.mydelivery.store.StoreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StoreController {

    @Autowired
    private StoreService storeService;

    // 해당 카테고리에 포함된 매장 검색
    @GetMapping("/store/{category}")
    public List<StoreEntity> getStoreList(@PathVariable String category) {
        return storeService.getStoreList(category);
    }

    // 매장의 상세정보 가져옴
    @GetMapping("/store/detail/{storeId}")
    public StoreEntity getStore(@PathVariable int storeId) {
        return storeService.getStore(storeId);
    }
}
