package com.delivery.mydelivery.register;

import com.delivery.mydelivery.user.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterRepository extends CrudRepository<UserEntity, Integer> {

    public UserEntity findByEmail(String email); // 이메일로 검색
    public UserEntity findByEmailAndPw(String email, String pw); // 이메일, 비밀번호로 검색

}
