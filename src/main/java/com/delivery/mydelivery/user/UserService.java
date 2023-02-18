package com.delivery.mydelivery.user;

import com.delivery.mydelivery.order.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    // 유저검색
    public UserEntity getUser(int userId) {
        return userRepository.findById(userId);
    }

    // 모든 학교 검색후 학교명 반환
    public List<String> getAllSchool() {
        List<SchoolEntity> schoolEntityList = new ArrayList<>();
        Streamable.of(schoolRepository.findAll()).forEach(schoolEntityList::add);

        List<String> schoolList = new ArrayList<>();
        for (SchoolEntity school : schoolEntityList) {
            schoolList.add(school.getSchoolName());
        }

        return schoolList;
    }

    // 이메일로 유저 검색
    public UserEntity findUser(String email) {
        return userRepository.findByEmail(email);
    }

    // 수정
    public UserEntity modify(UserEntity user) {
        return userRepository.save(user);
    }

}