package com.delivery.mydelivery.recruit;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitRepository extends CrudRepository<RecruitEntity, Integer> {

    RecruitEntity findByUserId(int userId); // 사용자 아이디로 검색

}
