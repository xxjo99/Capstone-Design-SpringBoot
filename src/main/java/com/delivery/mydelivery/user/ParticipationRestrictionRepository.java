package com.delivery.mydelivery.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipationRestrictionRepository extends CrudRepository<ParticipationRestrictionEntity, Integer> {

    ParticipationRestrictionEntity findByUserId(int userId);
}
