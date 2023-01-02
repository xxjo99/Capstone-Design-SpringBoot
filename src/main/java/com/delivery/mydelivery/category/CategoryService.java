package com.delivery.mydelivery.category;

import com.delivery.mydelivery.store.StoreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    // 넘겨받은 카테고리 데이터를 통해 매장 검색
    public List<StoreEntity> findStore(String category) {
        List<StoreEntity> stores = new ArrayList<>();
        stores = repository.findByCategory(category);
        return stores;
    }
}
