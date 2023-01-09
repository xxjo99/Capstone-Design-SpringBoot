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

    @GetMapping("/store/{category}")
    public List<StoreEntity> getStoreList(@PathVariable String category) {
        return storeService.getStoreList(category);
    }

    @GetMapping("/store/detail/{storeId}")
    public StoreEntity getStore(@PathVariable int storeId) {
        return storeService.getStore(storeId);
    }
}
