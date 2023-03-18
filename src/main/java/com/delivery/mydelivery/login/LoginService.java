package com.delivery.mydelivery.login;

import com.delivery.mydelivery.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    // 아이디와 비밀번호를 통해 로그인
    public UserEntity login(String email, String pw) {
        return loginRepository.findByEmailAndPw(email, pw);
    }

}
