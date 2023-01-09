package com.delivery.mydelivery.register;

import com.delivery.mydelivery.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RegisterService {

    @Autowired
    private RegisterRepository repository;

    // 이메일 중복검사, true, false로 검색
    public boolean findUserByEmail(String email) {
        UserEntity user = repository.findByEmail(email);

        if (user == null) { // 존재하지 않으면 true 반환
            return true;
        } else { // 존재하면 false 반환
            return false;
        }
    }

    // 사용자 추가
    public UserEntity save(UserEntity user) {
        return repository.save(user);
    }

}
