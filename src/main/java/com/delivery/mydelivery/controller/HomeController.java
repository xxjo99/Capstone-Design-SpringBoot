package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.home.CategoryEntity;
import com.delivery.mydelivery.home.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private HomeService homeService;

    // 카테고리 리스트 가져옴
    @GetMapping("/home/categoryList")
    public List<CategoryEntity> getCategoryList() {
        return homeService.getCategoryList();
    }

}
