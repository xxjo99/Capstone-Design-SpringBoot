package com.delivery.mydelivery.orderHistory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryRepository extends CrudRepository<OrderHistoryEntity, Integer> {

    List<OrderHistoryEntity> findByUserId(int userId);

    OrderHistoryEntity findByRecruitIdAndUserId(int recruitId, int userId);

}
