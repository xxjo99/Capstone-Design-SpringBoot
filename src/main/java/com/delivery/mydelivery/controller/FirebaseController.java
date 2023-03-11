package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.firebase.FirebaseCloudMessageService;
import com.delivery.mydelivery.firebase.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FirebaseController {

    @Autowired
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    // 알림 전송
    @PostMapping("/fcm/send")
    public void sendMessage(@RequestBody MessageDTO messageDTO) throws IOException {

        firebaseCloudMessageService.sendMessage(
                messageDTO.getTargetToken(),
                messageDTO.getTitle(),
                messageDTO.getBody());
    }

    // 배달접수 알림 전송
//    @PostMapping("/fcm/send/deliveryReception")
//    public String sendMessageDeliveryReception(@RequestParam("recruitId") int recruitId) throws IOException {
//        firebaseCloudMessageService.sendMessageDeliveryReception(recruitId);
//        return "deliveryReception";
//    }

    // 삭제 알림 전송
    @PostMapping("/fcm/send/deliveryReception")
    public void sendMessageDeleteRecruit(@RequestParam("recruitId") int recruitId) throws IOException {
        firebaseCloudMessageService.sendMessageDeleteRecruit(recruitId);
    }

}
