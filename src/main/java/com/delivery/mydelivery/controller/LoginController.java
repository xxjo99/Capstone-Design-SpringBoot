package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.login.LoginService;
import com.delivery.mydelivery.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login/{email}/{pw}") // 아이디와 비밀번호로 GET방식으로 로그인
    public UserEntity login(@PathVariable String email, @PathVariable String pw) {
        return loginService.login(email, pw);
    }

}
