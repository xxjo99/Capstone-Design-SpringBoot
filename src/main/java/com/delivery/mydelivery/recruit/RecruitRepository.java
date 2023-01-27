package com.delivery.mydelivery.recruit;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitRepository extends CrudRepository<RecruitEntity, Integer> {

    List<RecruitEntity> findByRegistrantPlace(String registrantPlace);

    RecruitEntity findByUserId(int userId); // 사용자 아이디로 검색

    RecruitEntity findByRecruitId(int recruitId);

}
