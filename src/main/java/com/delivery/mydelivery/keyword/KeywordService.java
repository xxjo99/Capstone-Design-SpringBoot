package com.delivery.mydelivery.keyword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordService {

    @Autowired
    private KeywordRepository keywordRepository;

    // 키워드 등록
    public void addKeyword(int userId, String keyword) {
        KeywordEntity keywordEntity = new KeywordEntity();
        keywordEntity.setUserId(userId);
        keywordEntity.setKeyword(keyword);

        keywordRepository.save(keywordEntity);
    }

    // 키워드 삭제
    public void deleteKeyword(int userId, String keyword) {
        KeywordEntity keywordEntity = keywordRepository.findByUserIdAndKeyword(userId, keyword);
        keywordRepository.delete(keywordEntity);
    }

    // 등록한 키워드 조회
    public List<KeywordEntity> getKeywordList(int userId) {
        return keywordRepository.findByUserId(userId);
    }

}
