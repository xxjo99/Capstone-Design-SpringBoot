package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.firebase.FirebaseCloudMessageService;
import com.delivery.mydelivery.firebase.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FirebaseController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;

    // 알림 전송
    @PostMapping("/firebase/fcm")
    public ResponseEntity pushMessage(@RequestBody MessageDTO messageDTO) throws IOException {

        firebaseCloudMessageService.sendMessageTo(
                messageDTO.getTargetToken(),
                messageDTO.getTitle(),
                messageDTO.getBody());

        return ResponseEntity.ok().build();
    }

}
