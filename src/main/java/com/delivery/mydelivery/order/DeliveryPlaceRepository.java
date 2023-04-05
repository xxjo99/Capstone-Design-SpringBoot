package com.delivery.mydelivery.order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryPlaceRepository extends CrudRepository<DeliveryPlaceEntity, Integer> {

    List<DeliveryPlaceEntity> findByPlaceContaining(String keyword);

}
