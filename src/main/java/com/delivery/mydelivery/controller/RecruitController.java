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

    @GetMapping("/recruit/getAllRecruit")
    public List<RecruitEntity> getRecruitList() {
        return recruitService.getRecruitList();
    }

    @GetMapping("/recruit/findRecruit/user/{userId}")
    public Boolean findRecruit(@PathVariable int userId) {
        return recruitService.findRecruit(userId);
    }

    @GetMapping("/recruit/getParticipantCount/{recruitId}")
    public int getParticipantCount(@PathVariable int recruitId) {
        return recruitService.getParticipantCount(recruitId);
    }

    @GetMapping("/recruit/findUser/{recruitId}/{userId}")
    public boolean findUserInRecruit(@PathVariable int recruitId, @PathVariable int userId) {
        return recruitService.findUserInRecruit(recruitId, userId);
    }

    @PostMapping("/recruit/participate")
    public ParticipantEntity participate(@RequestBody ParticipantEntity participant) {
        return recruitService.participate(participant);
    }

}
