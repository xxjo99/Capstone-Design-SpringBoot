package com.delivery.mydelivery.login;

import com.delivery.mydelivery.user.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends CrudRepository<UserEntity, Integer> {

    // 이메일, 비밀번호로 검색
    UserEntity findByEmailAndPw(String email, String pw);

}
