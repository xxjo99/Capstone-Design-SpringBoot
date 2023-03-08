package com.delivery.mydelivery.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private ParticipationRestrictionRepository participationRestrictionRepository;

    // 유저검색
    public UserEntity getUser(int userId) {
        return userRepository.findByUserId(userId);
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

    // 로그인한 기기의 토큰 저장
    public void setToken(String email, String token) {
        UserEntity user = userRepository.findByEmail(email);
        user.setToken(token);
        userRepository.save(user);
    }

    // 유저의 이용제한 생성
    public void setParticipationRestriction(int userId) {
        LocalDateTime currentTime = LocalDateTime.now();

        // 제한이 되어있는지 검색, 이미 제한되어있다면 24시간을 추가로 설정
        ParticipationRestrictionEntity participationRestriction = participationRestrictionRepository.findByUserId(userId);
        if (participationRestriction != null) {
            LocalDateTime restrictionPeriod = participationRestriction.getRestrictionPeriod().toLocalDateTime();
            LocalDateTime newRestrictionPeriod = restrictionPeriod.plusHours(24);
            participationRestriction.setRestrictionPeriod(Timestamp.valueOf(newRestrictionPeriod));

            participationRestrictionRepository.save(participationRestriction);
        } else {
            participationRestriction = new ParticipationRestrictionEntity();

            participationRestriction.setUserId(userId);
            Timestamp restrictionPeriod = Timestamp.valueOf(currentTime.plusHours(24));
            participationRestriction.setRestrictionPeriod(restrictionPeriod);

            participationRestrictionRepository.save(participationRestriction);
        }

    }

    // 이용제한 시간 반환
    public ParticipationRestrictionEntity getParticipationRestriction(int userId) {
        return participationRestrictionRepository.findByUserId(userId);
    }

    // 이용제한 지난지 확인, 지났으면 이용제한 제거
    public Boolean checkRestriction(int userId) {
        ParticipationRestrictionEntity participationRestriction = participationRestrictionRepository.findByUserId(userId);

        if (participationRestriction == null) { // 이용제한 없다면 true 반환
            return true;
        } else { // 이용제한 기간 지난지 검사
            LocalDateTime restrictionPeriod = (participationRestriction.getRestrictionPeriod()).toLocalDateTime();

            // 이용제한 지난지 확인
            if (LocalDateTime.now().isAfter(restrictionPeriod)) { // 지났다면 이용제한 제거 후 true 반환
                participationRestrictionRepository.delete(participationRestriction);
                return true;
            } else { // 그렇지않다면 false 반환
                return false;
            }
        }
    }

    // 포인트 차감
    public void deductPoint(int userId, int deductPoint) {
        UserEntity user = userRepository.findByUserId(userId);
        user.setPoint(user.getPoint() - deductPoint);
        userRepository.save(user);
    }

}