package com.delivery.mydelivery.controller;

import com.delivery.mydelivery.keyword.KeywordEntity;
import com.delivery.mydelivery.keyword.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KeywordController {

    @Autowired
    KeywordService keywordService;

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
}
