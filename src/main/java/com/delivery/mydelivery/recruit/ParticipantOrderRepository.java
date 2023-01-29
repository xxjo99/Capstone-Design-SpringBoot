package com.delivery.mydelivery.recruit;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParticipantOrderRepository extends CrudRepository<ParticipantOrderEntity, Integer> {

    // 해당 글에 등록된 유저의 메뉴 리스트 반환
    List<ParticipantOrderEntity> findByRecruitIdAndParticipantId(int recruitId, int participantId);

}
