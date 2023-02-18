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

    // 해당 카테고리에 포함된 오픈한 매장 검색
    @GetMapping("/store/open/{category}/{deliveryAvailablePlace}")
    public List<StoreEntity> getOpenedStoreList(@PathVariable String category, @PathVariable String deliveryAvailablePlace) {
        return storeService.getOpenedStoreList(category, deliveryAvailablePlace);
    }

    // 해당 카테고리에 포함된 마감한 매장 검색
    @GetMapping("/store/close/{category}/{deliveryAvailablePlace}")
    public List<StoreEntity> getClosedStoreList(@PathVariable String category, @PathVariable String deliveryAvailablePlace) {
        return storeService.getClosedStoreList(category, deliveryAvailablePlace);
    }

    // 매장의 상세정보 가져옴
    @GetMapping("/store/detail/{storeId}")
    public StoreEntity getStore(@PathVariable int storeId) {
        return storeService.getStore(storeId);
    }

    // 오픈한 매장 검색
    @GetMapping("/store/search/open/{keyword}/{deliveryAvailablePlace}")
    public List<StoreEntity> searchOpenedStore(@PathVariable String keyword, @PathVariable String deliveryAvailablePlace) {
        return storeService.searchOpenStore(keyword, deliveryAvailablePlace);
    }
}
