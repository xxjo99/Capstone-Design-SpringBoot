package com.delivery.mydelivery.order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Integer> {

    List<OrderEntity> findByUserIdAndStoreIdNot(int userId, int storeId); // 해당 매장이 아닌 다른 매장이 있는지 확인

    List<OrderEntity> findByUserId(int userId); // 장바구니에 담긴 메뉴를 가져옴
}
