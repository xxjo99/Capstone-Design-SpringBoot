package com.delivery.mydelivery.recruit;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParticipantRepository extends CrudRepository<ParticipantEntity, Integer> {

    List<ParticipantEntity> findByRecruitId(int recruitId);

    ParticipantEntity findByRecruitIdAndUserId(int recruitId, int userId);

    List<ParticipantEntity> findByUserId(int userId);
}
