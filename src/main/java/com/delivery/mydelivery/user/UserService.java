package com.delivery.mydelivery.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private SchoolRepository schoolRepository;

    // 유저검색
    public UserEntity getUser(int userId) {
        return userRepository.findById(userId);
    }

    // 모든 학교 검색
    public List<SchoolEntity> getAllSchool() {
        List<SchoolEntity> schoolList = new ArrayList<>();
        Streamable.of(schoolRepository.findAll()).forEach(schoolList::add);
        return  schoolList;
    }

}
