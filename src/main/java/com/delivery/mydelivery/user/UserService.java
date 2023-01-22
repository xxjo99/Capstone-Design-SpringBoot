package com.delivery.mydelivery.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    // 유저검색
    public UserEntity getUser(int userId) {
        return repository.findById(userId);
    }

}
