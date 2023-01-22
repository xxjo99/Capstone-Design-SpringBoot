package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.user.UserEntity;
import com.delivery.mydelivery.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/findUser/{userId}")
    public UserEntity getUser(@PathVariable int userId) {
        return userService.getUser(userId);
    }
}
