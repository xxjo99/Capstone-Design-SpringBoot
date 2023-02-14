package com.delivery.mydelivery.point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class PointService {

    @Autowired
    private PointHistoryRepository pointHistoryRepository;

    // 포인트 충전내역 추가
    public void addPointHistory(PointHistoryEntity pointHistory) {
        pointHistory.setDateTime(Timestamp.valueOf(LocalDateTime.now()));
        pointHistoryRepository.save(pointHistory);
    }

    // 포인트 이용내역
    public List<PointHistoryEntity> getPointHistory(int userId) {
        return pointHistoryRepository.findByUserId(userId);
    }

}
