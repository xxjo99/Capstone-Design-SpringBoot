package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.delivery.DeliveryService;
import com.delivery.mydelivery.firebase.FirebaseCloudMessageService;
import com.delivery.mydelivery.recruit.RecruitEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class DeliveryController {

    @Autowired
    private FirebaseCloudMessageService firebaseCloudMessageService;
    @Autowired
    private DeliveryService deliveryService;

    // 접수된 주문 리스트
    @GetMapping("/delivery/deliveryList")
    public List<RecruitEntity> getDeliveryList() {
        return deliveryService.getDeliveryList();
    }

    // 배달 시작 알림 전송, 모집글 배달 상태 변경
    @PostMapping("/delivery/start")
    public void receiptDelivery(@RequestParam("recruitId") int recruitId) throws IOException {
        // 1. 알림 전송
        firebaseCloudMessageService.sendMessageStartDelivery(recruitId);
        // 2. 배달 상태 변경
        deliveryService.setDeliveryStateStartDelivery(recruitId);
    }

    // 배달 출발한 모집글 리스트
    @GetMapping("/delivery/started/deliveryList")
    public List<RecruitEntity> getStartedDeliveryList() {
        return deliveryService.getStartedDeliveryList();
    }

}
