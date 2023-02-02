package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.user.SchoolEntity;
import com.delivery.mydelivery.user.UserEntity;
import com.delivery.mydelivery.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    // 유저 검색
    @GetMapping("/user/findUser/{userId}")
    public UserEntity getUser(@PathVariable int userId) {
        return userService.getUser(userId);
    }

    // 모든 학교 검색
    @GetMapping("/user/getAllSchool")
    public List<String> getAllSchool() {
        return userService.getAllSchool();
    }
}
