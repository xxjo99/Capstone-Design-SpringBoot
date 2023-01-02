package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.category.CategoryService;
import com.delivery.mydelivery.store.StoreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/category/{category}")
    public List<StoreEntity> setStoreCategory(@PathVariable String category) {
        return categoryService.findStore(category);
    }
}
