package com.delivery.mydelivery.register;

import com.delivery.mydelivery.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    // 아이디와 비밀번호를 통해 로그인
    public UserEntity login(String email, String pw) {
        UserEntity user = repository.findByEmailAndPw(email, pw);
        return user;
    }

    // 모든 사용자 정보 가져오기
    public List<UserEntity> getAllUser() {
        List<UserEntity> users = new ArrayList<>();

        Streamable.of(repository.findAll())
                .forEach(user -> {
                    users.add(user);
                });
        return users;
    }

}
