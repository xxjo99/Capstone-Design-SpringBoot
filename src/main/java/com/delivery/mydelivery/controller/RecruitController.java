package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.recruit.ParticipantEntity;
import com.delivery.mydelivery.recruit.RecruitEntity;
import com.delivery.mydelivery.recruit.RecruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    // 모든 모집글 검색
    @GetMapping("/recruit/getAllRecruit")
    public List<RecruitEntity> getRecruitList() {
        return recruitService.getRecruitList();
    }

    // 해당 사용자의 등록글이 있는지 검색
    @GetMapping("/recruit/findRecruit/user/{userId}")
    public Boolean findRecruit(@PathVariable int userId) {
        return recruitService.findRecruit(userId);
    }

    // 해당 매장의 참가인원 반환
    @GetMapping("/recruit/getParticipantCount/{recruitId}")
    public int getParticipantCount(@PathVariable int recruitId) {
        return recruitService.getParticipantCount(recruitId);
    }

    // 해당모집글에 해당 유저가 존재하는지 확인
    @GetMapping("/recruit/findUser/{recruitId}/{userId}")
    public boolean findUserInRecruit(@PathVariable int recruitId, @PathVariable int userId) {
        return recruitService.findUserInRecruit(recruitId, userId);
    }

    // 해당 모집글에 참가
    @PostMapping("/recruit/participate")
    public ParticipantEntity participate(@RequestBody ParticipantEntity participant) {
        return recruitService.participate(participant);
    }

    // 해당 유저가 참가한 글 검색
    @GetMapping("/recruit/findRecruitList/{userId}")
    public List<RecruitEntity> findRecruitList(@PathVariable int userId) {
        return recruitService.findRecruitList(userId);
    }

}
