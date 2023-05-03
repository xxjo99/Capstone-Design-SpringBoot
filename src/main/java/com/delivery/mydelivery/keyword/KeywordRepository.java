package com.delivery.mydelivery.keyword;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRepository extends CrudRepository<KeywordEntity, Integer> {

    KeywordEntity findByUserIdAndKeyword(int userId, String keyword);

    List<KeywordEntity> findByUserId(int userId);

}
