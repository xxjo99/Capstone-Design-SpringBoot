package com.delivery.mydelivery.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    @Autowired
    private CategoryRepository repository;

    // 카테고리 리스트 받아옴
    public List<CategoryEntity> getCategoryList() {
        return repository.findAll();
    }

}
