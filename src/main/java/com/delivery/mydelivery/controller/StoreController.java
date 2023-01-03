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
    StoreService storeService;

    @GetMapping("/storeList/{category}")
    public List<StoreEntity> setStoreList(@PathVariable String category) {
        return storeService.getStoreList(category);
    }
}
