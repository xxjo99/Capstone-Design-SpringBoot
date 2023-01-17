package com.delivery.mydelivery.order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitRepository extends CrudRepository<RecruitEntity, Integer> {
}
