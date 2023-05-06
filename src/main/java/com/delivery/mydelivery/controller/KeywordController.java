package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.firebase.FirebaseCloudMessageService;
import com.delivery.mydelivery.keyword.KeywordEntity;
import com.delivery.mydelivery.keyword.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class KeywordController {

    @Autowired
    KeywordService keywordService;

    @Autowired
    private FirebaseCloudMessageService firebaseCloudMessageService;

    // 키워드 등록
    @PostMapping("/keyword/add/{userId}/{keyword}")
    public void addKeyword(@PathVariable int userId, @PathVariable String keyword) {
        keywordService.addKeyword(userId, keyword);
    }

    // 키워드 삭제
    @DeleteMapping("/keyword/delete/{userId}/{keyword}")
    public void deleteKeyword(@PathVariable int userId, @PathVariable String keyword) {
        keywordService.deleteKeyword(userId, keyword);
    }

    // 등록한 키워드 조회
    @GetMapping("/keyword/list/{userId}")
    public List<KeywordEntity> getKeywordList(@PathVariable int userId) {
        return keywordService.getKeywordList(userId);
    }

    // 키워드 알림 전송
    @PostMapping("/keyword/send/message")
    public void sendKeywordMessage(@RequestParam("keyword") String keyword, @RequestParam("storeName") String storeName) throws IOException {
        firebaseCloudMessageService.sendKeywordMessage(keyword, storeName);
    }
}
