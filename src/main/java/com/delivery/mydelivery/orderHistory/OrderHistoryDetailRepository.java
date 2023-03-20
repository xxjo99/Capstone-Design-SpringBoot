package com.delivery.mydelivery.orderHistory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryDetailRepository extends CrudRepository<OrderHistoryDetailEntity, Integer> {

    List<OrderHistoryDetailEntity> findByRecruitIdAndUserId(int recruitId, int userId);

}
