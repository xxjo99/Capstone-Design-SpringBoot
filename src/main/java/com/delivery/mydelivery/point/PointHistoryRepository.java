package com.delivery.mydelivery.point;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointHistoryRepository extends CrudRepository<PointHistoryEntity, Integer> {

    List<PointHistoryEntity> findByUserId(int userId);

}
