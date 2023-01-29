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

    // 해당 유저 위치에 맞는 모집글 검색
    @GetMapping("/recruit/getRecruitList/{registrantPlace}")
    public List<RecruitEntity> getRecruitList(@PathVariable String registrantPlace) {
        return recruitService.getRecruitList(registrantPlace);
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

    // 해당 글에 참가한 구성원 리스트 반환
    @GetMapping("/recruit/getParticipantList/{recruitId}")
    public List<ParticipantEntity> getParticipantList(@PathVariable int recruitId) {
        return recruitService.getParticipantList(recruitId);
    }

    // 해당 참가자가 담은 메뉴의 총 금액 반환
    @GetMapping("/recruit/getOrdersTotalPrice/{recruitId}/{participantId}")
    public int getOrdersTotalPrice(@PathVariable int recruitId, @PathVariable int participantId) {
        return recruitService.getOrdersTotalPrice(recruitId, participantId);
    }

    // 자신을 제외한 나머지 참가자 리스트 반환
    @GetMapping("/recruit/getParticipantListExceptMine/{recruitId}/{userId}")
    public List<ParticipantEntity> getParticipantListExceptMine(@PathVariable int recruitId, @PathVariable int userId) {
        return recruitService.getParticipantListExceptMine(recruitId, userId);
    }

}
