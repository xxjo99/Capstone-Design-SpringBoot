package com.delivery.mydelivery.register;

import com.delivery.mydelivery.user.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterRepository extends CrudRepository<UserEntity, Integer> {

    UserEntity findByEmail(String email); // 이메일로 검색

}
