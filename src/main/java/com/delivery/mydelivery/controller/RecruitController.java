package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.recruit.ParticipantEntity;
import com.delivery.mydelivery.recruit.ParticipantOrderEntity;
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
    @GetMapping("/recruit/recruits/{registrantPlace}")
    public List<RecruitEntity> getRecruitList(@PathVariable String registrantPlace) {
        return recruitService.getRecruitList(registrantPlace);
    }

    // 해당 사용자의 등록글이 있는지 검색
    @GetMapping("/recruit/find/user/{userId}")
    public Boolean findRecruit(@PathVariable int userId) {
        return recruitService.findRecruit(userId);
    }

    // 해당 매장의 참가인원 반환
    @GetMapping("/recruit/participants/count/{recruitId}")
    public int getParticipantCount(@PathVariable int recruitId) {
        return recruitService.getParticipantCount(recruitId);
    }

    // 해당모집글에 해당 유저가 존재하는지 확인
    @GetMapping("/recruit/find/user/{recruitId}/{userId}")
    public boolean findUserInRecruit(@PathVariable int recruitId, @PathVariable int userId) {
        return recruitService.findUserInRecruit(recruitId, userId);
    }

    // 해당 모집글에 참가
    @PostMapping("/recruit/participate")
    public ParticipantEntity participate(@RequestBody ParticipantEntity participant) {
        return recruitService.participate(participant);
    }

    // 해당 유저가 참가한 글 검색
    @GetMapping("/recruit/find/recruits/{userId}")
    public List<RecruitEntity> findRecruitList(@PathVariable int userId) {
        return recruitService.findRecruitList(userId);
    }

    // 해당 글에 참가한 구성원 리스트 반환
    @GetMapping("/recruit/participants/{recruitId}")
    public List<ParticipantEntity> getParticipantList(@PathVariable int recruitId) {
        return recruitService.getParticipantList(recruitId);
    }

    // 해당 참가자가 담은 메뉴의 총 금액 반환
    @GetMapping("/recruit/price/{recruitId}/{participantId}")
    public int getOrdersTotalPrice(@PathVariable int recruitId, @PathVariable int participantId) {
        return recruitService.getOrdersTotalPrice(recruitId, participantId);
    }

    // 자신을 제외한 나머지 참가자 리스트 반환
    @GetMapping("/recruit/participants/except/{recruitId}/{userId}")
    public List<ParticipantEntity> getParticipantListExceptMine(@PathVariable int recruitId, @PathVariable int userId) {
        return recruitService.getParticipantListExceptMine(recruitId, userId);
    }

    // 최종결제금액
    @GetMapping("/recruit/payment/{recruitId}/{storeId}/{userId}")
    public int getFinalPayment(@PathVariable int recruitId, @PathVariable int storeId, @PathVariable int userId) {
        return recruitService.getFinalPayment(recruitId, storeId, userId);
    }

    // 해당 글에서 해당 유저가 담은 메뉴 반환
    @GetMapping("/recruit/orders/{recruitId}/{participantId}")
    public List<ParticipantOrderEntity> getOrderList(@PathVariable int recruitId, @PathVariable int participantId) {
        return recruitService.getOrderList(recruitId, participantId);
    }

    // 개수 수정
    @PostMapping("/recruit/modify/amount")
    public ParticipantOrderEntity modifyAmount(@RequestBody ParticipantOrderEntity order) {
        return recruitService.modifyAmount(order);
    }

    // 메뉴 삭제
    @DeleteMapping("/recruit/delete/menu/{participantOrderId}")
    public void deleteOrder(@PathVariable int participantOrderId) {
        recruitService.deleteMenu(participantOrderId);
    }

    // 메뉴 추가
    @PostMapping("/recruit/save")
    public ParticipantOrderEntity addMenu(@RequestBody ParticipantOrderEntity order) {
        return recruitService.addMenu(order);
    }

    // 모집글 검색
    @GetMapping("/recruit/search/{keyword}/{deliveryAvailablePlace}")
    public List<RecruitEntity> searchRecruit(@PathVariable String keyword, @PathVariable String deliveryAvailablePlace) {
        return recruitService.searchRecruit(keyword, deliveryAvailablePlace);
    }

    // 모집글 삭제
    @DeleteMapping("/recruit/delete/recruit/{recruitId}")
    public void deleteRecruit(@PathVariable int recruitId) {
        recruitService.deleteRecruit(recruitId);
    }

    // 등록자 검색
    @GetMapping("/recruit/find/registrant/{recruitId}")
    public ParticipantEntity findRegistrant(@PathVariable int recruitId) {
        return recruitService.findRegistrant(recruitId);
    }

}
