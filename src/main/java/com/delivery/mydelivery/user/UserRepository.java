package com.delivery.mydelivery.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    UserEntity findByUserId(int userId); // 유저 검색
    UserEntity findByEmail(String email); // 유저 검색

}
